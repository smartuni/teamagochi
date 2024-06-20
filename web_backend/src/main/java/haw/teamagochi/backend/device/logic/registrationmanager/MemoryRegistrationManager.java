package haw.teamagochi.backend.device.logic.registrationmanager;

import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Setter;
import org.jboss.logging.Logger;

/** In-memory implementation of a {@link RegistrationManager}. */
@ApplicationScoped
public class MemoryRegistrationManager implements RegistrationManager {

  private static final Logger LOGGER = Logger.getLogger(MemoryRegistrationManager.class);

  private static final int DEFAULT_TIMEOUT_SECONDS = 600;

  /** LwM2M clients (devices) which are waiting for registration. */
  private final HashMap<String, Date> clientMap;

  /** Maps registration codes to endpoint client names. */
  private final HashMap<String, String> registrationCodeMap;

  @Setter
  private Integer registrationLifetime;

  public MemoryRegistrationManager() {
    clientMap = new HashMap<>();
    registrationCodeMap = new HashMap<>();
  }

  @PostConstruct
  void init() {
    LOGGER.debug("Initialized MemoryRegistrationManager instance.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String registerClient(String registrationCode) {
    String endpoint = registrationCodeMap.remove(registrationCode);

    if (endpoint != null) {
      LOGGER.info(
          "The client '"
              + endpoint
              + "' was registered with code '"
              + registrationCode
              + "'.");
    }

    return endpoint;
  }

  /**
   * {@inheritDoc}
   *
   * @param endpoint is the "Endpoint Client Name" of a LwM2M client
   */
  @Override
  public void addClient(String endpoint) {
    Date lastUpdate = clientMap.put(endpoint, new Date());

    // map.put() returns null if a new entry was added to the map
    if (lastUpdate == null) {

      // Generate registration codes until the created key is not already in the keySet
      String registrationCode;
      do {
        registrationCode = generateRegistrationCode();
      } while (registrationCodeMap.containsKey(registrationCode));

      registrationCodeMap.put(registrationCode, endpoint);

      LOGGER.info(
          "The client '"
              + endpoint
              + "' is available for registration (timeout: "
              + getTimeoutUpperBound() + " seconds, code: "
              + registrationCode + ").");
    } else {
      String registrationCode = registrationCodeMap.entrySet().stream()
          .filter(entry -> endpoint.equals(entry.getValue()))
          .map(Map.Entry::getKey).findFirst().orElse(null);

      LOGGER.info("The timer for '"
              + endpoint
              + "' has been reset (timeout: "
              + getTimeoutUpperBound() + " seconds, code: "
              + registrationCode + ").");
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
          "Removed outdated clients: "
              + String.join(", ", outdatedClients)
              + " ("
              + clientMap.size()
              + " active remaining)");
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
   * Generate a registration code.
   *
   * @return a pseudo-random alphabetic string
   */
  private String generateRegistrationCode() {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 8;
    Random random = new Random();

    return random.ints(leftLimit, rightLimit + 1)
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
