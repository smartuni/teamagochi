package haw.teamagochi.backend.device.service.rest.v1.mapper;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="cdi")
public interface DeviceMapper {
  DeviceMapper MAPPER = Mappers.getMapper(DeviceMapper.class);
  @Mapping(source = "deviceID", target = "id")
  @Mapping(source = "deviceName", target = "name")
  @Mapping(source = "DeviceType.valueOf(deviceType)", target = "deviceType")
  DeviceEntity toResource(DeviceDTO device);

  @InheritInverseConfiguration
  DeviceDTO fromResource(DeviceEntity device);



}
