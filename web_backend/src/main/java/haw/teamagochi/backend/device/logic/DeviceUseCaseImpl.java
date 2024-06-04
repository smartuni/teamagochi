package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DeviceUseCaseImpl implements DeviceUseCase {

  @Inject
  DeviceRepository deviceRepository;

  @Inject
  UserRepository userRepository;

  //@Inject
  //RegistrationManager registrationManager; //TODO: Merlin. vllt. Injectable machen? Weil nur eine Instanz erlaubt.

  @Transactional
  public DeviceEntity createDevice(String name, DeviceType deviceType) {
    DeviceEntity device = new DeviceEntity(name, deviceType);
    deviceRepository.persist(device);
    return device;
  }

  @Transactional
  public boolean deviceExists(long id) {
    DeviceEntity device = deviceRepository.findById(id);
    return device != null;
  }

  @Transactional
  public DeviceEntity getDevice(long deviceID) {
    return deviceRepository.findById(deviceID);
  }

  @Transactional
  public List<DeviceEntity> getDevices(long userID) {
    UserEntity user = userRepository.findById(userID);
    if (user == null) throw new NullPointerException("User not found in database.");

    return deviceRepository.findByOwner(user);
  }

  @Transactional
  public void deleteAll() {
    deviceRepository.deleteAll();
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
