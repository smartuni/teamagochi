package haw.teamagochi.backend.pet.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcManagePetType;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetTypeMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetTypeDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PetTypeMapper}.
 */
@QuarkusTest
public class PetTypeMapperTest {

  @Inject
  UcManagePetType ucManagePetType;

  PetTypeMapper petTypeMapper = PetTypeMapper.MAPPER;

  private PetTypeEntity petTypeEntity;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    petTypeEntity = ucManagePetType.createPetType("Dolphin");
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    petTypeEntity = null;
    ucManagePetType.deleteAll();
  }

  @Test
  void testMapEntityToTransferObject() {
    // Given
    PetTypeEntity entity = petTypeEntity;

    // When
    PetTypeDTO dto = petTypeMapper.mapEntityToTransferObject(entity);

    // Then
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getName(), dto.getName());
  }

  @Test
  void testMapTransferObjectToEntity() {
    // Given
    PetTypeDTO dto = new PetTypeDTO(petTypeEntity.getId(), petTypeEntity.getName());

    // When
    PetTypeEntity entity = petTypeMapper.mapTransferObjectToEntity(dto);

    // Then
    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getName(), entity.getName());
  }
}
