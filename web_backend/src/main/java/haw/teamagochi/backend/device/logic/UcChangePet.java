package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;

public interface UcChangePet {

  /**
   *
   * @param deviceId id of the device
   * @param petId id of pet to be put on the device
   * @return the Device Entity
   */
  DeviceEntity changePet(long deviceId, long petId);

}
