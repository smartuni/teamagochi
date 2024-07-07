package haw.teamagochi.backend.device.logic.devicemanager;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcDeviceResourceOperations;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcFindLeshanClient;
import haw.teamagochi.backend.device.logic.UcPetResourceOperations;
import haw.teamagochi.backend.device.logic.clients.rest.DeviceStatus;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ClientDto;
import haw.teamagochi.backend.pet.logic.petmanager.PetManager;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.logging.Logger;

/**
 * In-memory implementation of a {@link DeviceManager}.
 */
@ApplicationScoped
@Startup
public class MemoryDeviceManager implements DeviceManager {

  private static final Logger LOGGER = Logger.getLogger(MemoryDeviceManager.class);

  private boolean initialized;

  @Inject
  UcFindDevice ucFindDevice;

  @Inject
  UcFindLeshanClient ucFindLeshanClient;

  @Inject
  UcPetResourceOperations ucPetResourceOperations;

  @Inject
  UcDeviceResourceOperations ucDeviceResourceOperations;

  @Inject
  PetManager petManager;

  Set<String> activeDevices;

  Map<String, Long> devices;

  Map<String, Long> pets;

  public MemoryDeviceManager() {
    initialized = false;
    activeDevices = new HashSet<>();
    devices = new HashMap<>();
    pets = new HashMap<>();
  }

  /**
   * Initialize the manager.
   */
  @PostConstruct
  public void init() {
    if (initialized) {
      return;
    }

    List<ClientDto> clientDtos = ucFindLeshanClient.getClients();
    for (ClientDto client : clientDtos) {
      DeviceEntity entity = ucFindDevice.findByIdentifier(client.endpoint);
      if (entity != null) {
        add(entity.getIdentifier(), entity.getId());
      }
    }

    initialized = true;

    LOGGER.debug("Initialized MemoryDeviceManager instance.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(String endpoint) {
    return devices.containsKey(endpoint);
  }

  /**
   * {@inheritDoc}
   *
   * <p>A device is added as soon as the registration was successful.
   */
  @Override
  public void add(String endpoint, Long deviceId) {
    devices.put(endpoint, deviceId);
    enableDevice(endpoint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove(String endpoint) {
    devices.remove(endpoint);
    activeDevices.remove(endpoint);
  }

  @Override
  public void removeAll() {
    devices.clear();
    activeDevices.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disableDevice(String endpoint) {
    activeDevices.remove(endpoint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void enableDevice(String endpoint) {
    if (activeDevices.contains(endpoint)) {
      // Should be idempotent, may be called multiple times
      return;
    }

    DeviceEntity entity = ucFindDevice.findByIdentifier(endpoint);

    if (writePetToDevice(entity)) {
      activeDevices.add(endpoint);

      assert entity.getPet() != null;
      LOGGER.info("Enabled device " + endpoint + " with pet " + entity.getPet().getName());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void reloadDevice(String endpoint) {
    activeDevices.remove(endpoint);

    enableDevice(endpoint);
  }

  /**
   * Write pet object of a given entity to the registered client device.
   *
   * @param entity the device which should be written
   * @return true on success, otherwise false
   */
  private boolean writePetToDevice(DeviceEntity entity) {
    try {
      if (entity != null && entity.getPet() != null) {
        ucPetResourceOperations.writePet(entity.getIdentifier(), entity.getPet());

        // TODO not always needed, right?
        ucDeviceResourceOperations.writeStatus(entity.getIdentifier(), DeviceStatus.READY);
        ucPetResourceOperations.observePetInteractions(entity.getIdentifier());

        if (!petManager.contains(entity.getPet().getId())) {
          petManager.add(entity.getPet());
          pets.put(entity.getIdentifier(), entity.getPet().getId());
        }

        return true;
      }
    } catch (Exception exception) {
      LOGGER.error("Could not write pet to device", exception);
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getActiveDevices() {
    return activeDevices.stream().map(endpoint -> devices.get(endpoint)).toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getActivePetByClientEndpointName(String endpoint) {
    if (endpoint == null) {
      throw new IllegalArgumentException("Endpoint must not be null");
    }

    return pets.get(endpoint);
  }
}
