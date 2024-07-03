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
   * Update a device.
   *
   * @param entity to update
   * @return the updated entity
   */
  DeviceEntity update(DeviceEntity entity);

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

  /**
   * Register a device.
   *
   * @param registrationCode displayed on the device
   * @param name for the created device
   * @param type for the created device
   * @param uuid of the owner
   * @return the created entity
   */
  DeviceEntity registerDevice(String registrationCode, String name, String type, String uuid);
}
