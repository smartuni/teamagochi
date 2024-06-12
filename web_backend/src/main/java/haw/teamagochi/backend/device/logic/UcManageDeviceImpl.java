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

  @Inject
  UcFindUser ucFindUser;

  // TODO uncomment if https://github.com/smartuni/teamagochi/pull/100 was merged
  //@Inject
  //RegistrationManager registrationManager;

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
  public void deleteAll() {
    deviceRepository.deleteAll();
  }

  @Override
  @Transactional
  public void deleteDevice(long id){
    deviceRepository.deleteById(id);
  }

  /*
  public boolean registerDevice(long userID, String key) {
    long deviceID = registrationManager.getDevice(key);
    if (deviceID == -1) return false;

    UserEntity user = userRepository.findById(userID);
    DeviceEntity device = deviceRepository.findById(deviceID);

    if (user == null) throw new NullPointerException("Tried to register device to user, but USER not found in database.");
    if (device == null) throw new NullPointerException("Tried to register device to user, but DEVICE not found in database.");

    device.setOwner(user);
    return true;
  }
  */
}
