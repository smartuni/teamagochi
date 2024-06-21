package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
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
    registrationManager.clearCache();
    return deviceRepository.deleteById(deviceId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void deleteAll() {
    registrationManager.clearCache();
    deviceRepository.deleteAll();
  }

  /**
   * {@inheritDoc}
   * TODO whe have no way to determine the device type here. Lets default to "FROG".
   */
  @Override
  @Transactional
  public DeviceEntity registerDevice(String registrationCode, String deviceName, String uuid) {
    String endpoint = registrationManager.registerClient(registrationCode);
    if (endpoint == null) {
      return null;
    }

    UserEntity owner = ucFindUser.find(uuid);
    if (owner == null) {
      owner = ucManageUser.create(uuid); // create userId in database
    }

    DeviceEntity device = new DeviceEntity(deviceName, DeviceType.FROG);
    device.setOwner(owner);
    device.setIdentifier(endpoint); // Jessica

    return create(device);
  }
}
