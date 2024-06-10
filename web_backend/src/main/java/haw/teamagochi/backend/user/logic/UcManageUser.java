package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import java.util.UUID;

/**
 * Operations to manage users.
 */
public interface UcManageUser {

  /**
   * Similar to {@link UcManageUser#create(UUID)}.
   */
  UserEntity create(String uuid);

  /**
   * Create a user.
   *
   * @param uuid the system-wide id of the user
   * @return persisted user object
   */
  UserEntity create(UUID uuid);

  /**
   * Delete all users.
   */
  void deleteAll();
}
