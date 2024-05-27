package haw.teamagochi.backend.app.Services;

import haw.teamagochi.backend.app.Mapper.DeviceDTO;
import java.util.List;

public interface DeviceAPIServiceInterface {


  public List<DeviceDTO> getUserDevices(long userID);
  public DeviceDTO getDevice(long userID, long deviceID);
  public DeviceDTO registerDevice(long userID, String registrationKey, String deviceName);
  public DeviceDTO renameDevice (long userID, long DeviceID, String deviceName);

}
