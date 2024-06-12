package haw.teamagochi.backend.device.logic;


import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;

/**
 * Operations to manage devices.
 */
public interface UcManageDevice {

  /**
   * Create a device.
   *
   * @param name of the device
   * @param deviceType of the device
   * @return the created entity
   */
  DeviceEntity create(String name, DeviceType deviceType);

  /**
   * Delete all devices.
   */
  void deleteAll();

  void deleteDevice(long id);
}
