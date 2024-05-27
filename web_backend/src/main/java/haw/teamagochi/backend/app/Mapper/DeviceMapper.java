package haw.teamagochi.backend.app.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="cdi")
public interface DeviceMapper {

  @Mapping(target = "deviceID", source = "id")
  @Mapping(target = "deviceName", source = "name")

  DeviceDTO toResource(Device device);

}
