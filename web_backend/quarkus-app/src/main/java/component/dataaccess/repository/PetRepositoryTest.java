package component.dataaccess.repository;

import component.dataaccess.model.PetEntity;
import component.dataaccess.model.PetTypeEntity;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
public class PetRepositoryTest {

  @Inject
  PetRepository petRepository;

  @Inject
  PetTypeRepository petTypeRepository;

  private static PetEntity defaultPet;
  private static PetTypeEntity defaultPetType;

  @BeforeAll
  public static void setUp() {
    defaultPetType = new PetTypeEntity();
    defaultPet = new PetEntity(
        "petname",
        "fakecolor",
        defaultPetType
    );
  }

  @Test
  @Transactional
  public void testRepositoryAccess() {
    petRepository.persist(defaultPet);
    petTypeRepository.persist(defaultPetType);

    PetEntity newPet = petRepository.findByName(defaultPet.getName());
    Assertions.assertEquals(defaultPet, newPet);
  }

}
