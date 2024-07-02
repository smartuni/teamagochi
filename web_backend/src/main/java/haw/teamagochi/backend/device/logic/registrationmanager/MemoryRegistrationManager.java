package haw.teamagochi.backend.device.logic.registrationmanager;

import haw.teamagochi.backend.device.logic.UcDeviceResourceOperations;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.clients.rest.DeviceStatus;
import haw.teamagochi.backend.device.logic.devicemanager.DeviceManager;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import lombok.Setter;
import org.jboss.logging.Logger;

/** In-memory implementation of a {@link RegistrationManager}. */
@ApplicationScoped
@Startup
public class MemoryRegistrationManager implements RegistrationManager {

  private static final Logger LOGGER = Logger.getLogger(MemoryRegistrationManager.class);

  private static final int DEFAULT_TIMEOUT_SECONDS = 600;

  /** LwM2M clients (devices) which are waiting for registration. */
  private final HashMap<String, Date> clientMap;

  /** Maps registration codes to endpoint client names. */
  private final HashMap<String, String> registrationCodeMap;

  @Inject
  UcFindDevice ucFindDevice;

  @Inject
  UcDeviceResourceOperations ucDeviceResourceOperations;

  @Inject
  DeviceManager deviceManager;

  @Setter
  private Integer registrationLifetime;

  public MemoryRegistrationManager() {
    clientMap = new HashMap<>();
    registrationCodeMap = new HashMap<>();
  }

  @PostConstruct
  void init() {
    deviceManager.init();
    LOGGER.debug("Initialized MemoryRegistrationManager instance.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getClientByCode(String registrationCode) {
    return registrationCodeMap.get(registrationCode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean updateClient(String endpoint, Long deviceId) {
    if (!clientMap.containsKey(endpoint)) {
      throw new IllegalStateException("Client is not available for registration.");
    }

    boolean result = ucDeviceResourceOperations.writeStatus(endpoint, DeviceStatus.REGISTERED);

    if (result) {
      removeClient(endpoint);
      deviceManager.addDevice(endpoint, deviceId);

      LOGGER.info("The client '" + endpoint + "' was registered.");
    }

    return result;
  }

  /**
   * {@inheritDoc}
   *
   * @param endpoint is the "Endpoint Client Name" of a LwM2M client
   */
  @Override
  @Transactional
  public void addClient(String endpoint) {
    if (!isClientAllowedForRegistration(endpoint)) return;

    // Map.put() returns null if a new entry was added
    Date lastUpdate = clientMap.put(endpoint, new Date());

    if (lastUpdate == null) {
      /*
       * Client did not register successfully until now:
       *
       *  1. Generate registration codes until the created key is not already in the keySet
       *  2. Write code to the device
       *  3. Store code-to-device association
       */
      try {
        String registrationCode = generateUniqueRegistrationCode(registrationCodeMap.keySet());
        writeRegistrationCodeToDevice(endpoint, registrationCode);
        registrationCodeMap.put(registrationCode, endpoint);

        LOGGER.info(
            "The client '" + endpoint + "' is available for registration (timeout: "
                + getTimeoutUpperBound() + " seconds, code: " + registrationCode + ").");
      } catch (Exception e) {
        clientMap.remove(endpoint);
      }
    } else {
      /*
       * Client is already known and a registration code should exist.
       */
      String registrationCode = registrationCodeMap.entrySet().stream()
          .filter(entry -> endpoint.equals(entry.getValue()))
          .map(Map.Entry::getKey).findFirst().orElse(null);

      LOGGER.info(
          "The timer for '" + endpoint + "' has been reset (timeout: " + getTimeoutUpperBound()
              + " seconds, code: " + registrationCode + ").");
    }
  }

  /**
   * Check if a client should be processed by the registration manager.
   *
   * @param endpoint to check status for
   * @return true if it should be added, otherwise false.
   */
  private boolean isClientAllowedForRegistration(String endpoint) {
    return !deviceManager.hasDevice(endpoint);
  }

  /**
   * Write a registration code to a client where it is displayed for further usage.
   *
   * @param endpoint to write the code to
   * @param registrationCode which should be written
   */
  private void writeRegistrationCodeToDevice(String endpoint, String registrationCode) {
    int maxRetries = 9;
    int retryIntervalMs = 1000;
    int count = 0;
    boolean success = false;

    while (!success) {
      try {
        success = ucDeviceResourceOperations.writeRegistrationCode(endpoint, registrationCode);
        if (!success) {
          throw new RuntimeException();
        }
      } catch (RuntimeException e) {
        LOGGER.info("Could not write to device. Retry in "
            + retryIntervalMs + "ms (" + (count + 1) + "/" + (maxRetries + 1) + ").");

        if (++count == maxRetries) {
          throw new RuntimeException("Could not write registration code to device", e);
        }

        try {
          // Just another smelling sleep
          Thread.sleep(retryIntervalMs);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @param endpoint is the "Endpoint Client Name" of a LwM2M client
   */
  @Override
  public void removeClient(String endpoint) {
    clientMap.remove(endpoint);
    registrationCodeMap.values().remove(endpoint);
  }

  /**
   * {@inheritDoc}
   */
  @Scheduled(every = "30s")
  public void removeOutdatedClients() {
    List<String> outdatedClients = findOutdatedClients();

    if (!outdatedClients.isEmpty()) {
      outdatedClients.forEach(this::removeClient);

      LOGGER.info(
          "Removed outdated clients: " + String.join(", ", outdatedClients)
              + " (" + clientMap.size() + " active remaining)");
    }
  }

  /**
   * Amount of clients waiting available for registration.
   *
   * @return the amount of clients
   */
  public int size() {
    int clientMapSize = clientMap.size();
    if (clientMapSize != registrationCodeMap.size()) {
      throw new IllegalStateException("Size mismatch of internal storages.");
    }
    return clientMapSize;
  }

  @Override
  public void clearCache() {}

  /**
   * Find outdated clients.
   *
   * @return a list of endpoint names
   */
  private List<String> findOutdatedClients() {
    return clientMap.entrySet().stream()
        .filter(
            client -> {
              long seconds = (new Date().getTime() - client.getValue().getTime()) / 1000;
              return seconds >= getTimeoutUpperBound();
            })
        .map(Map.Entry::getKey)
        .toList();
  }

  private int getTimeoutUpperBound() {
    return registrationLifetime != null ? registrationLifetime : DEFAULT_TIMEOUT_SECONDS;
  }

  /**
   * Generate a registration code which is not already in a given set.
   *
   * @return a pseudo-random alphabetic uppercase string
   */
  private String generateUniqueRegistrationCode(Set<String> existingCodes) {
    String registrationCode;
    do {
      registrationCode = generateRegistrationCode();
    } while (existingCodes.contains(registrationCode));

    return registrationCode;
  }

  /**
   * Generate a registration code.
   *
   * @return a pseudo-random alphabetic uppercase string
   */
  private String generateRegistrationCode() {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 8;
    Random random = new Random();

    return random.ints(leftLimit, rightLimit + 1)
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString().toUpperCase();
  }
}
