package haw.teamagochi.backend.device.service.rest.v1.mapper;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="cdi")
public interface DeviceMapper {

  @Mapping(target = "id", source = "deviceID")
  @Mapping(target = "name", source = "deviceName")
  DeviceDTO toResource(DeviceEntity device);

}
