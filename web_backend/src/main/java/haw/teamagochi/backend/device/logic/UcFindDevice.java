package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import java.util.List;
import java.util.Optional;

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
   * Find device by id.
   *
   * @param id of the device
   * @return the optional entity
   */
  Optional<DeviceEntity> findOptional(long id);

  /**
   * Find device by LwM2M identifier.
   *
   * @param identifier of the device
   * @return entity if found, otherwise null
   */
  DeviceEntity findByIdentifier(String identifier);

  /**
   * Find all devices.
   *
   * @return entities if found, otherwise empty list
   */
  List<DeviceEntity> findAll();

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

  /**
   * Find devices by its owners system-wide id.
   *
   * @param uuid of the user who owns the device.
   * @return entities if found, otherwise empty list
   */
  List<DeviceEntity> findAllByExternalUserId(String uuid);
}
