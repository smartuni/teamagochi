package haw.teamagochi.backend.app.Mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel="cdi")
public interface DeviceMapper {

  DeviceDTO toResource(Device device);

}
