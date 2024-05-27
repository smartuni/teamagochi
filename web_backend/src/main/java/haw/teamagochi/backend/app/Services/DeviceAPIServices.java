package haw.teamagochi.backend.app.Services;

import haw.teamagochi.backend.app.Mapper.DeviceDTO;
import haw.teamagochi.backend.app.Mapper.DeviceMapper;
import java.util.List;

public class DeviceAPIServices implements DeviceAPIServiceInterface{

  RegistrationManager registrationManager;
  DeviceAPIServices(RegistrationManager registrationManager){
    this.registrationManager = registrationManager;
  }
  @Override
  public List<DeviceDTO> getUserDevices(long userID) {
    return null;
  }

  @Override
  public DeviceDTO getDevice(long userID, long deviceID) {
    return null;
  }

  @Override
  public DeviceDTO registerDevice(long userID, String registrationKey, String deviceName) {
    long deviceID = registrationManager.getDevice(registrationKey);
    //TODO --> check return (valid Key or not), put device into DB, create DeviceDTO, return DeviceDTO/error
    //return DeviceMapper.toResource(deviceServiceImpl.getDeviceEntity(deviceID));
    return null;
  }

  @Override
  public DeviceDTO renameDevice(long userID, long DeviceID, String deviceName) {
    return null;
  }
}
