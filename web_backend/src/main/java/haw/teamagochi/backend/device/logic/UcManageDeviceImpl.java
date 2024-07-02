package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.device.logic.devicemanager.DeviceManager;
import haw.teamagochi.backend.device.logic.registrationmanager.RegistrationManager;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import haw.teamagochi.backend.user.logic.UcManageUser;
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

  @Inject
  UcFindUser ucFindUser;

  @Inject
  UcManageUser ucManageUser;

  @Inject
  RegistrationManager registrationManager;

  @Inject
  DeviceManager deviceManager;

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
    try {
      DeviceEntity entity = deviceRepository.findById(deviceId);
      deviceManager.removeDevice(entity.getIdentifier());
      deviceRepository.delete(entity);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void deleteAll() {
    deviceManager.removeAll();
    deviceRepository.deleteAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public DeviceEntity registerDevice(String registrationCode, String deviceName, String deviceType, String uuid) {
    String endpoint = registrationManager.getClientByCode(registrationCode);
    if (endpoint == null) {
      return null;
    }

    UserEntity owner = ucFindUser.find(uuid);
    if (owner == null) {
      owner = ucManageUser.create(uuid);
    }

    DeviceType type;
    try {
      type = DeviceType.valueOf(deviceType.toUpperCase());
    } catch (IllegalArgumentException e) {
      type = DeviceType.FROG;
    }

    DeviceEntity device = new DeviceEntity(deviceName, type);
    device.setOwner(owner);
    device.setIdentifier(endpoint);
    create(device);

    registrationManager.updateClient(endpoint, device.getId());

    return device;
  }
}
