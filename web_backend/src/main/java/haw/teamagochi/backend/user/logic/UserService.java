package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import java.util.UUID;

public interface UserService {

  UserEntity createUser(UUID uuid);

  void deleteAll();

}
