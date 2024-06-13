package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.user.logic.UcFindUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Default implementation for {@link UcManageDevice}.
 */
@ApplicationScoped
public class UcManageDeviceImpl implements UcManageDevice {

  @Inject
  DeviceRepository deviceRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public DeviceEntity create(String name, DeviceType deviceType) {
    DeviceEntity device = new DeviceEntity(name, deviceType);
    deviceRepository.persist(device);
    return device;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public DeviceEntity create(DeviceEntity entity) {
    deviceRepository.persist(entity);
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public boolean deleteById(long deviceId) {
    return deviceRepository.deleteById(deviceId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void deleteAll() {
    deviceRepository.deleteAll();
  }
}
