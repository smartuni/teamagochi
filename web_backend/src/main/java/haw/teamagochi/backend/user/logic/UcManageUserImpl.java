package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;

/**
 * Default implementation for {@link UcManageUser}.
 */
@ApplicationScoped
public class UcManageUserImpl implements UcManageUser {

  @Inject
  UserRepository userRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public UserEntity create(String uuid) {
    return create(UUID.fromString(uuid));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public UserEntity create(UUID uuid) {
    UserEntity user = new UserEntity();
    user.setExternalID(uuid);
    userRepository.persist(user);
    return user;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void deleteAll() {
    userRepository.deleteAll();
  }
}
