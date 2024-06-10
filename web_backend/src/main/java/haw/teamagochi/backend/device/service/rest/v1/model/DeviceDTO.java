package haw.teamagochi.backend.device.service.rest.v1.model;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetInfoDTO;
import lombok.Getter;

@Getter
public class DeviceDTO {
  long deviceID;
  String deviceName;
  //String deviceType;

  public DeviceDTO (long deviceID, String deviceName){//} String deviceType){
      this.deviceID = deviceID;
      this.deviceName = deviceName;
      //this.deviceType = deviceType;
  }
}
