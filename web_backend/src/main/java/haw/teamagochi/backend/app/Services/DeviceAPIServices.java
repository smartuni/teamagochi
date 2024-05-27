package haw.teamagochi.backend.app.Services;

import haw.teamagochi.backend.app.Mapper.DeviceDTO;
import haw.teamagochi.backend.app.Mapper.DeviceMapper;
import java.util.List;

public class DeviceAPIServices implements DeviceAPIServiceInterface{
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
    long deviceID = registrationManager.getDevice(registerCode);
    //TODO --> check return (valid Key or not), put device into DB, create DeviceDTO, return DeviceDTO/error
    return DeviceMapper.toResource(//TODO);
  }

  @Override
  public DeviceDTO renameDevice(long userID, long DeviceID, String deviceName) {
    return null;
  }
}
