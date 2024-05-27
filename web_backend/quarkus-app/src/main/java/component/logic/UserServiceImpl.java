package component.logic;

import component.dataaccess.model.UserEntity;
import component.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl {

  @Inject
  UserRepository userRepository;

  public UserEntity createUser(UUID uuid) {
    UserEntity user = new UserEntity();
    userRepository.persist(user);
    return user;
  }
}
