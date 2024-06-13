package haw.teamagochi.backend.pet.service.rest.v1.mapper;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcFindPetType;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between {@link PetEntity} and {@link PetDTO}.
 */
@Mapper(componentModel="cdi")
public interface PetMapper {

  PetMapper MAPPER = Mappers.getMapper(PetMapper.class);

  /**
   * Map a transfer object to an entity.
   *
   * @param petDto as the mapping source
   * @return an entity
   */
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "type", target = "petType.id")
  @Mapping(source = "state.happiness", target = "happiness")
  @Mapping(source = "state.wellbeing", target = "wellbeing")
  @Mapping(source = "state.health", target = "health")
  @Mapping(source = "state.hunger", target = "hunger")
  @Mapping(source = "state.cleanliness", target = "cleanliness")
  @Mapping(source = "state.fun", target = "fun")
  @Mapping(source = "state.xp", target = "xp")
  PetEntity mapTransferObjectToEntity(PetDTO petDto);

  /**
   * See {@link PetMapper#mapTransferObjectToEntity(PetDTO)}.
   */
  List<PetEntity> mapTransferObjectToEntity(List<PetDTO> petDtos);

  /**
   * Map an entity to a transfer object.
   *
   * @param petEntity as the mapping source
   * @return a transfer object (dto)
   */
  @InheritInverseConfiguration
  PetDTO mapEntityToTransferObject(PetEntity petEntity);

  /**
   * See {@link PetMapper#mapEntityToTransferObject(PetEntity)}.
   */
  List<PetDTO> mapEntityToTransferObject(List<PetEntity> petEntities);

  @ObjectFactory
  default PetTypeEntity lookup(Long id, @Context UcFindPetType ucFindPetType) {
    return ucFindPetType.find(id);
  }
}
