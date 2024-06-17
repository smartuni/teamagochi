package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation for {@link UcFindDevice}.
 */
@ApplicationScoped
public class UcFindDeviceImpl implements UcFindDevice {

  @Inject
  DeviceRepository deviceRepository;

  @Inject
  UcFindUser ucFindUser;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists(long id) {
    return deviceRepository.findById(id) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DeviceEntity find(long id) {
    return deviceRepository.findById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<DeviceEntity> findOptional(long id) {
    return deviceRepository.findByIdOptional(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DeviceEntity> findAll() {
    return deviceRepository.findAll().stream().toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DeviceEntity> findAllByUser(UserEntity user) {
    return deviceRepository.findByOwner(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<DeviceEntity> findAllByUserId(long userId) {
    UserEntity user = ucFindUser.find(userId);
    if (user == null) {
      throw new NotFoundException("User was not found.");
    }

    return deviceRepository.findByOwner(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<DeviceEntity> findAllByExternalUserId(String uuid) {
    UserEntity user = ucFindUser.find(uuid);
    if (user == null) {
      throw new NotFoundException("User was not found.");
    }

    return deviceRepository.findByOwner(user);
  }
}
