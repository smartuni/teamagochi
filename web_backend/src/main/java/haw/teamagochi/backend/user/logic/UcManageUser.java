package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import java.util.UUID;

public interface UcManageUser {

  /**
   * Creates a user and saves it persistently in the database.
   * Any future changes to the user object's attributes will be updated automatically in the database.
   * @param uuid the UUID for the user
   * @return persisted user object
   */
  UserEntity createUser(UUID uuid);

  void deleteAll();

}
