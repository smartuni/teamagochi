package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import java.util.List;

/**
 * Operations to find users.
 */
public interface UcFindUser {

  /**
   * Find user by id.
   *
   * @param id of the user
   * @return entity if found, otherwise null
   */
  UserEntity find(long id);

  /**
   * Find user by id.
   *
   * @param uuid the system-wide id of the user
   * @return entity if found, otherwise null
   */
  UserEntity find(String uuid);

  /**
   * Find all users.
   *
   * @return entities if found, otherwise empty list
   */
  List<UserEntity> findAll();
}
