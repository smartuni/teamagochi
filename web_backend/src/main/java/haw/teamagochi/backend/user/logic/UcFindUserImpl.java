package haw.teamagochi.backend.user.logic;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Default implementation for {@link UcFindUser}.
 */
@ApplicationScoped
public class UcFindUserImpl implements UcFindUser {

  @Inject
  UserRepository userRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity find(long id) {
    return userRepository.findById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity find(String uuid) {
    return userRepository.findByExternalId(UUID.fromString(uuid));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<UserEntity> findAll() {
    return userRepository.findAll().stream().toList();
  }
}
