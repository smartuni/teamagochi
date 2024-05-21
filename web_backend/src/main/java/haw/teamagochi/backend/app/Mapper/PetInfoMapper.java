package haw.teamagochi.backend.app.Mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel="cdi")
public interface PetInfoMapper {

  PetInfoDTO toResource(PetInfoDTO petInfoDTO);
}
