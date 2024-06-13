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
   * Create a device.
   *
   * @param entity to create
   * @return the created entity
   */
  DeviceEntity create(DeviceEntity entity);

  /**
   * Delete a device.
   *
   * @param deviceId of the device
   * @return true if entity was deleted, otherwise false (not found)
   */
  boolean deleteById(long deviceId);

  /**
   * Delete all devices.
   */
  void deleteAll();
}
