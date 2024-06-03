package haw.teamagochi.backend.app.Mapper;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="cdi")
public interface DeviceMapper {

  @Mapping(target = "id", source = "deviceID")
  @Mapping(target = "name", source = "deviceName")

  DeviceDTO toResource(DeviceEntity device);

}
