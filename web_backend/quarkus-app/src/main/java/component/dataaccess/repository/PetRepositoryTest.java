package component.dataaccess.repository;

import component.dataaccess.model.MockEntity;
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


  private static PetEntity defaultPet;
  private static PetTypeEntity defaultPetType;

  @BeforeAll
  public static void beforeAll() {
    defaultPetType = new PetTypeEntity();
    defaultPet = new PetEntity(
        "petname",
        //"fakecolor",
        defaultPetType
    );
  }


  @BeforeEach
  @Transactional
  public void beforeEach() {

    // Deletion order important?
    PetEntity.deleteAll();
    PetTypeEntity.deleteAll();


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

    defaultPetType.persist();
    defaultPet.persist();

    String oldName = defaultPet.getName();
    String newName = "new pet name";

    defaultPet.setName(newName);
    PetEntity persistedPet = PetEntity.findById(defaultPet.id);

    Assertions.assertEquals(newName, persistedPet.getName());
  }

}
