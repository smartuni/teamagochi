package haw.teamagochi.backend.device.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.device.service.rest.v1.mapper.DeviceMapper;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.UcManagePetType;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DeviceMapper}.
 */
@QuarkusTest
public class DeviceMapperTest {

  @Inject
  UcFindDevice ucFindDevice;

  @Inject
  UcManageDevice ucManageDevice;

  @Inject
  UcFindUser ucFindUser;

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcFindPet ucFindPet;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcManagePetType ucManagePetType;

  DeviceMapper deviceMapper = DeviceMapper.MAPPER;

  private Map<String, DeviceEntity> deviceEntities;

  private UserEntity owner;

  private PetEntity pet;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    owner = ucManageUser.create(UUID.randomUUID());
    PetTypeEntity petType = ucManagePetType.createPetType("Frog");
    pet = ucManagePet.create(owner.getId(), "Fifi", petType.getId());

    deviceEntities = new HashMap<>();
    DeviceEntity device0 = new DeviceEntity("device-0", DeviceType.FROG);
    device0.setOwner(owner);
    device0.setPet(pet);
    device0.setIdentifier("dev0-lwm2m-endpoint-id");
    deviceEntities.put(device0.getName(), device0);
    deviceEntities.put("device-1", new DeviceEntity("device-1", DeviceType.FROG));
    deviceEntities.put("device-2", new DeviceEntity("device-2", DeviceType.FROG));
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    owner = null;
    pet = null;
    deviceEntities = null;
    ucManageDevice.deleteAll();
    ucManagePet.deleteAll();
    ucManagePetType.deleteAll();
    ucManageUser.deleteAll();
  }

  @Test
  public void testMapEntityToTransferObject() {
    // Given
    DeviceEntity entity = deviceEntities.get("device-0");

    // When
    DeviceDTO dto = deviceMapper.mapEntityToTransferObject(entity);

    // Then
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getName(), dto.getName());
    assertEquals("frog", dto.getType());
    assertEquals(String.valueOf(this.owner.getExternalID()), dto.getOwnerId());
    assertEquals(this.pet.getId(), dto.getPetId());
  }

  @Test
  public void testMapEntityListToTransferObjectList() {
    // Given
    List<DeviceEntity> entities = deviceEntities.values().stream().toList();

    // When
    List<DeviceDTO> dtos = deviceMapper.mapEntityToTransferObject(entities);

    // Then
    assertEquals(entities.size(), dtos.size());

    for (int i = 0; i < entities.size(); i++) {
      DeviceEntity entity = entities.get(i);
      DeviceDTO dto = dtos.get(i);

      assertEquals(entity.getId(), dto.getId());
      assertEquals(entity.getName(), dto.getName());
    }
  }

  @Test
  @Transactional
  public void testMapTransferObjectToEntity() {
    // Given
    String deviceName = "device-0";
    DeviceEntity device0 = deviceEntities.get(deviceName);
    ucManageDevice.create(device0);

    assert device0.getOwner() != null;
    assert device0.getPet() != null;

    DeviceDTO dto = new DeviceDTO(device0.getId(), deviceName, "frog",
        String.valueOf(device0.getOwner().getExternalID()), device0.getPet().getId());

    // When
    DeviceEntity entity =
        deviceMapper.mapTransferObjectToEntity(dto, ucFindUser, ucFindPet, ucFindDevice);

    // Then
    assert entity.getOwner() != null;
    assert entity.getPet() != null;

    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getName(), entity.getName());
    assertEquals(DeviceType.FROG, entity.getDeviceType());
    assertEquals(device0.getOwner().getExternalID(), entity.getOwner().getExternalID());
    assertEquals(device0.getIdentifier(), entity.getIdentifier());
    assertEquals(device0.getOwner().getId(), entity.getOwner().getId());
    assertEquals(device0.getPet().getId(), entity.getPet().getId());
  }

  @Test
  public void testMapTransferObjectToEntity_WithoutOwnerAndPet() {
    // Given
    DeviceDTO dto = new DeviceDTO(1L, "device-0", "frog", null, null);

    // When
    DeviceEntity entity = deviceMapper.mapTransferObjectToEntity(dto, ucFindUser, ucFindPet);

    // Then
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getName(), entity.getName());
    assertEquals(DeviceType.FROG, entity.getDeviceType());
  }

  @Test
  void testMapStringToDeviceType() {
    // Given
    String typeString1 = "frog";
    String typeString2 = "FROG";
    String typeString3 = "Frog";

    // When
    DeviceType type1 = deviceMapper.mapStringToDeviceType(typeString1);
    DeviceType type2 = deviceMapper.mapStringToDeviceType(typeString2);
    DeviceType type3 = deviceMapper.mapStringToDeviceType(typeString3);

    // Then
    assertEquals(DeviceType.FROG, type1);
    assertEquals(DeviceType.FROG, type2);
    assertEquals(DeviceType.FROG, type3);
  }

  @Test
  void mapDeviceTypeToString() {
    // Given
    DeviceType type = DeviceType.FROG;

    // When
    String typeString = deviceMapper.mapDeviceTypeToString(type);

    // Then
    assertEquals("frog", typeString);
  }
}
