package haw.teamagochi.backend.pet.dataaccess.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.JDBCException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import io.quarkus.test.h2.H2DatabaseTestResource;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class PetRepositoryTests {

  @Inject
  PetRepository petRepository;

  @Inject
  PetTypeRepository petTypeRepository;

  @Inject
  UserRepository userRepository;

  private static PetEntity defaultPet;
  private static PetTypeEntity defaultPetType;
  private static UserEntity defaultUser;

  @BeforeAll
  public static void beforeAll() {
    defaultPetType = new PetTypeEntity();
    defaultUser = new UserEntity();
    defaultPet = new PetEntity(
        defaultUser,
        "petname",
        //"fakecolor",
        defaultPetType
    );
  }


  @AfterEach
  @Transactional
  public void afterEach() {

    // Deletion order important?
    petRepository.deleteAll();
    petTypeRepository.deleteAll();
    userRepository.deleteAll();

  }


  /*
  @Test
  public void testColorRegex() {
    Assertions.assertThrows(Exception.class, ()-> defaultPet.setColor("wrong_color_syntax"));
  }
   */


  @Test
  @Transactional
  public void testHibernatePersistence() {

    userRepository.persist(defaultUser);
    petTypeRepository.persist(defaultPetType);
    petRepository.persist(defaultPet);

    String oldName = defaultPet.getName();
    String newName = "new pet name";

    defaultPet.setName(newName);
    PetEntity persistedPet = petRepository.findById(defaultPet.getId());

    Assertions.assertEquals(newName, persistedPet.getName());
  }

  @Test
  @Transactional
  public void testDeletePetEntityDoesNotDeletePetTypeEntity() {
    // Given
    PetTypeEntity petType = new PetTypeEntity();
    petType.setName("MyPetType");
    petTypeRepository.persist(petType);
    PetEntity entity = new PetEntity();
    entity.setPetType(petType);
    petRepository.persist(entity);

    // When
    Executable deleteFn = () -> petRepository.delete(entity);

    // Then
    assertDoesNotThrow(deleteFn);
    assertNotNull(petTypeRepository.findById(petType.getId()));
  }

  @Test
  @Transactional
  public void testDeletePetEntityDoesNotDeletePetTypeEntity2() {
    // Given
    PetTypeEntity petType = new PetTypeEntity();
    petType.setName("MyPetType");
    petTypeRepository.persist(petType);
    PetEntity entity = new PetEntity();
    entity.setPetType(petType);
    petRepository.persist(entity);

    // When
    Executable deleteFn = () -> {
      petTypeRepository.delete(petType);

      // Needed for exception to be thrown, otherwise assertThrows is always false
      petTypeRepository.flush();
    };

    // Then
    assertThrows(PersistenceException.class, deleteFn);
  }

}
