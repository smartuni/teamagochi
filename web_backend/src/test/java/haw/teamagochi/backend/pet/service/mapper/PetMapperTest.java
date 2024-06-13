package haw.teamagochi.backend.pet.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.UcManagePetType;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetTypeDTO;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PetMapper}.
 */
@QuarkusTest
public class PetMapperTest {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcManagePetType ucManagePetType;

  PetMapper petMapper = PetMapper.MAPPER;

  private Map<String, PetEntity> petEntities;

  private UserEntity owner;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    owner = ucManageUser.create(UUID.randomUUID());
    PetTypeEntity petType = ucManagePetType.createPetType("Frog");

    petEntities = new HashMap<>();
    PetEntity pet1 = ucManagePet.create(owner.getId(), "Fifi", petType.getId());

    pet1.setHappiness(10);
    pet1.setWellbeing(11);
    pet1.setHealth(12);
    pet1.setHunger(13);
    pet1.setCleanliness(14);
    pet1.setFun(15);
    pet1.setXp(16);

    petEntities.put("pet1", pet1);
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    petEntities = null;
    ucManagePet.deleteAll();
    ucManageUser.deleteAll();
    ucManagePetType.deleteAll();
  }

  @Test
  void testMapEntityToTransferObject() {
    // Given
    PetEntity entity = petEntities.get("pet1");

    // When
    PetDTO dto = petMapper.mapEntityToTransferObject(entity);

    // Then
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getPetType().getId(), dto.getType());

    assertEquals(entity.getHappiness(), dto.getState().getHappiness());
    assertEquals(entity.getWellbeing(), dto.getState().getWellbeing());
    assertEquals(entity.getHealth(), dto.getState().getHealth());
    assertEquals(entity.getHunger(), dto.getState().getHunger());
    assertEquals(entity.getCleanliness(), dto.getState().getCleanliness());
    assertEquals(entity.getFun(), dto.getState().getFun());
    assertEquals(entity.getXp(), dto.getState().getXp());
  }

  @Test
  void testMapTransferObjectToEntity() {
    // Given
    PetEntity sourceEntity = petEntities.get("pet1");
    PetTypeDTO petTypeDto =
        new PetTypeDTO(sourceEntity.getPetType().getId(), sourceEntity.getPetType().getName());
    PetStateDTO petStateDto = new PetStateDTO(10, 11, 12, 13, 14, 15, 16);
    PetDTO dto =
        new PetDTO(sourceEntity.getId(), sourceEntity.getName(), petTypeDto.getId(), petStateDto);

    // When
    PetEntity entity = petMapper.mapTransferObjectToEntity(dto);

    // Then
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getName(), entity.getName());
    assertEquals(dto.getType(), entity.getPetType().getId());

    assertEquals(dto.getState().getHappiness(), entity.getHappiness());
    assertEquals(dto.getState().getWellbeing(), entity.getWellbeing());
    assertEquals(dto.getState().getHealth(), entity.getHealth());
    assertEquals(dto.getState().getHunger(), entity.getHunger());
    assertEquals(dto.getState().getCleanliness(), entity.getCleanliness());
    assertEquals(dto.getState().getFun(), entity.getFun());
    assertEquals(dto.getState().getXp(), entity.getXp());
  }
}
