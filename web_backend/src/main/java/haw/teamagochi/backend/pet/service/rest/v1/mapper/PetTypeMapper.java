package haw.teamagochi.backend.pet.service.rest.v1.mapper;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetTypeDTO;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between {@link PetEntity} and {@link PetTypeDTO}.
 */
@Mapper(componentModel="cdi")
public interface PetTypeMapper {

  PetTypeMapper MAPPER = Mappers.getMapper(PetTypeMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  PetTypeEntity mapTransferObjectToEntity(PetTypeDTO petTypeDto);

  /**
   * See {@link PetTypeMapper#mapTransferObjectToEntity(PetTypeDTO)}.
   */
  List<PetTypeEntity> mapTransferObjectToEntity(List<PetTypeDTO> petTypeDtos);

  /**
   * Map an entity to a transfer object.
   *
   * @param petTypeEntity as the mapping source
   * @return a transfer object (dto)
   */
  @InheritInverseConfiguration
  PetTypeDTO mapEntityToTransferObject(PetTypeEntity petTypeEntity);

  /**
   * See {@link PetTypeMapper#mapEntityToTransferObject(PetTypeEntity)}.
   */
  List<PetTypeDTO> mapEntityToTransferObject(List<PetTypeEntity> petTypeEntities);
}
