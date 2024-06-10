package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import java.util.List;

/**
 * Operations to find devices.
 */
public interface UcFindDevice {

  /**
   * Returns whether device exists in the database.
   *
   * @param id of the device
   * @return true if it exists, otherwise false
   */
  boolean exists(long id);

  /**
   * Find device by id.
   *
   * @param id of the device
   * @return entity if found, otherwise null
   */
  DeviceEntity find(long id);

  /**
   * Find devices by its owner.
   *
   * @param user who owns the device
   * @return entity if found, otherwise null
   */
  List<DeviceEntity> findAllByUser(UserEntity user);

  /**
   * Find devices by its owners id.
   *
   * @param userId of the user who owns the device.
    * @return entities if found, otherwise empty list
   */
  List<DeviceEntity> findAllByUserId(long userId);
}
