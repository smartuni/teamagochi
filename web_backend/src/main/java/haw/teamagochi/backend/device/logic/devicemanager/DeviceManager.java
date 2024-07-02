package haw.teamagochi.backend.device.logic.devicemanager;

import java.util.List;

/**
 * Defines a device manager.
 *
 * <p>Caches devices which are currently active and registered with the LwM2m Server.
 */
public interface DeviceManager {

  /** Initialize */
  void init();

  /**
   * Check if a device is known to the manager.
   *
   * @param endpoint name of the client device
   */
  boolean hasDevice(String endpoint);

  /**
   * Add a device.
   *
   * @param endpoint name of the client device
   * @param deviceId for the device entity
   */
  void addDevice(String endpoint, Long deviceId);

  /**
   * Remove a device.
   *
   * @param endpoint name of the client device
   */
  void removeDevice(String endpoint);

  /**
   * Remove all devices.
   */
  void removeAll();

  /**
   * Disable a device.
   *
   * @param endpoint name of the client device
   */
  void disableDevice(String endpoint);

  /**
   * Enable a device.
   *
   * @param endpoint name of the client device
   */
  void enableDevice(String endpoint);

  /**
   * Get all active.
   *
   * @return all active devices
   */
  List<Long> getActiveDevices();
}
