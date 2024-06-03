package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class UserUseCaseImpl implements UserUseCase {

  @Inject
  UserRepository userRepository;

  public UserEntity createUser(UUID uuid) {
    UserEntity user = new UserEntity();
    userRepository.persist(user);
    return user;
  }

  public void deleteAll() {
    userRepository.deleteAll();
  }
}
