package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;

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
}
