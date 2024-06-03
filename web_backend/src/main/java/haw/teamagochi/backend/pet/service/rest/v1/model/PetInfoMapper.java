package haw.teamagochi.backend.pet.service.rest.v1.model;

import org.mapstruct.Mapper;

@Mapper(componentModel="cdi")
public interface PetInfoMapper {

  PetInfoDTO toResource(PetInfoDTO petInfoDTO);
}
