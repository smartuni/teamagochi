package haw.teamagochi.backend.device.logic.devicemanager;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcFindLeshanClient;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ClientDto;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.logging.Logger;

/** In-memory implementation of a {@link DeviceManager}. */
@ApplicationScoped
@Startup
public class MemoryDeviceManager implements DeviceManager {

  private static final Logger LOGGER = Logger.getLogger(MemoryDeviceManager.class);

  private boolean initialized;

  @Inject
  UcFindDevice ucFindDevice;

  @Inject
  UcFindLeshanClient ucFindLeshanClient;

  Set<String> activeDevices;

  Map<String, Long> devices;

  public MemoryDeviceManager() {
    initialized = false;
    activeDevices = new HashSet<>();
    devices = new HashMap<>();
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
        addDevice(entity.getIdentifier(), entity.getId());
      }
    }

    initialized = true;

    LOGGER.debug("Initialized MemoryDeviceManager instance.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasDevice(String endpoint) {
    return devices.containsKey(endpoint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addDevice(String endpoint, Long deviceId) {
    devices.put(endpoint, deviceId);
    activeDevices.add(endpoint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeDevice(String endpoint) {
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
  public void enableDevice(String endpoint) {
    activeDevices.add(endpoint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Long> getActiveDevices() {
    return activeDevices.stream().map(endpoint -> devices.get(endpoint)).toList();
  }
}
