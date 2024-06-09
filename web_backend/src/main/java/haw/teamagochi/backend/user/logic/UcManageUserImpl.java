package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class UcManageUserImpl implements UcManageUser {

  @Inject
  UserRepository userRepository;

  @Transactional
  public UserEntity createUser(UUID uuid) {
    UserEntity user = new UserEntity();
    userRepository.persist(user);
    return user;
  }

  @Transactional
  public void deleteAll() {
    userRepository.deleteAll();
  }
}
