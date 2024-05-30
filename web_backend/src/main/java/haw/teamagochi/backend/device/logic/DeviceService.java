package haw.teamagochi.backend.device.logic;


import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;

public interface DeviceService {

  /**
   * Creates a device and saves it persistently in the database.
   * Any future changes to the device object's attributes will be updated automatically in the database.
   * @param name name of the device
   * @param deviceType device type
   * @return persisted device object
   */
  DeviceEntity createDevice(String name, DeviceType deviceType);

  /**
   * Returns whether device exists in the database.
   * @param id id of the device
   * @return whether device was found
   */
  boolean deviceExists(long id);

  /**
   * Deletes all devices from the database.
   */
  void deleteAll();

}
