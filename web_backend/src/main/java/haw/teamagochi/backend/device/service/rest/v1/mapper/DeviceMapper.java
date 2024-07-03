package haw.teamagochi.backend.device.service.rest.v1.mapper;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between {@link DeviceEntity} and {@link DeviceDTO}.
 */
@Mapper(componentModel="cdi")
public interface DeviceMapper {

  DeviceMapper MAPPER = Mappers.getMapper(DeviceMapper.class);

  /**
   * Map a transfer object to an entity.
   *
   * @param deviceDto as the mapping source
   * @return an entity
   */
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "type", target = "deviceType")
  @Mapping(source = "ownerId", target = "owner.externalID")
  @Mapping(source = "petId", target = "pet.id")
  @Mapping(constant = "", target = "identifier")
  DeviceEntity mapTransferObjectToEntity(DeviceDTO deviceDto, @Context UcFindUser ucFindUser, @Context UcFindPet ucFindPet, @Context UcFindDevice ucFindDevice);

  default DeviceEntity mapTransferObjectToEntity(DeviceDTO deviceDto, @Context UcFindUser ucFindUser, @Context UcFindPet ucFindPet) {
    return mapTransferObjectToEntity(deviceDto, ucFindUser, ucFindPet, null);
  }

  /**
   * See {@link //DeviceMapper#mapTransferObjectToEntity(DeviceDTO, UcFindUser, UcFindPet)}.
   */
  List<DeviceEntity> mapTransferObjectToEntity(List<DeviceDTO> deviceDtos, @Context UcFindUser ucFindUser, @Context UcFindPet ucFindPet);

  /**
   * Map an entity to a transfer object.
   *
   * @param deviceEntity as the mapping source
   * @return a transfer object (dto)
   */
  @InheritInverseConfiguration
  DeviceDTO mapEntityToTransferObject(DeviceEntity deviceEntity);

  /**
   * See {@link DeviceMapper#mapEntityToTransferObject(DeviceEntity)}.
   */
  List<DeviceDTO> mapEntityToTransferObject(List<DeviceEntity> deviceEntities);

  /**
   * Map string to DeviceType.
   *
   * @param type is a string for conversion
   * @return the enum representation
   */
  @ValueMapping(source = "frog", target = "FROG")
  @ValueMapping(source = "FROG", target = "FROG")
  @ValueMapping(source = "Frog", target = "FROG")
  @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "UNDEFINED")
  DeviceType mapStringToDeviceType(String type);

  /**
   * Map DeviceType to string.
   *
   * @param deviceType for conversion
   * @return the string representation
   */
  @InheritInverseConfiguration
  String mapDeviceTypeToString(DeviceType deviceType);

  @AfterMapping
  default void findOwner(DeviceDTO dto, @MappingTarget DeviceEntity entity, @Context UcFindUser ucFindUser) {
    if (dto.getOwnerId() == null || ucFindUser == null) {
      entity.setOwner(null);
      return;
    }

    UserEntity dbEntity = ucFindUser.find(dto.getOwnerId());
    if (dbEntity != null) {
      entity.setOwner(dbEntity);
    }
  }

  @AfterMapping
  default void findPet(DeviceDTO dto, @MappingTarget DeviceEntity entity, @Context UcFindPet ucFindPet) {
    if (dto.getPetId() == null || ucFindPet == null) {
      entity.setPet(null);
      return;
    }

    PetEntity dbEntity = ucFindPet.find(dto.getPetId());
    if (dbEntity != null) {
      entity.setPet(dbEntity);
    }
  }

  @AfterMapping
  default void findDeviceIdentifier(@MappingTarget DeviceEntity entity, @Context UcFindDevice ucFindDevice) {
    if (ucFindDevice == null) {
      entity.setIdentifier(null);
      return;
    }

    DeviceEntity dbEntity = ucFindDevice.find(entity.getId());
    if (dbEntity != null) {
      entity.setIdentifier(dbEntity.getIdentifier());
    }
  }
}
