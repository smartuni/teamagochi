package haw.teamagochi.backend.pet.service.rest.v1.mapper;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="cdi")
public interface PetInfoMapper {

  PetInfoMapper MAPPER = Mappers.getMapper(PetInfoMapper.class);

  @Mapping(target = "petID", expression = "java(pet.getId())")
  @Mapping(target = "petName", expression = "java(pet.getName())")
  @Mapping(target = "petType", expression = "java(pet.getPetType().toString())")
  PetInfoDTO toResource(PetEntity pet);
}
