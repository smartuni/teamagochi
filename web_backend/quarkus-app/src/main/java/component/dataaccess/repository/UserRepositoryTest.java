package component.dataaccess.repository;

import component.dataaccess.model.DeviceEntity;
import component.dataaccess.model.PetEntity;
import component.dataaccess.model.PetTypeEntity;
import component.dataaccess.model.UserEntity;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UserRepositoryTest {


  private static UserEntity defaultUser;

  @BeforeAll
  public static void beforeAll() {
    UUID uuid = new UUID(1,1);
    defaultUser = new UserEntity(
        uuid
    );
  }

  @BeforeEach
  @Transactional
  public void beforeEach() {
    UserEntity.deleteAll();
  }

  @Test
  @Transactional
  public void testRepositoryAccess() {
    Assertions.assertDoesNotThrow(() -> {

      // create entities
      PetTypeEntity petType = new PetTypeEntity();
      PetEntity pet = new PetEntity(
          "petname",
          //"wrong_color_syntax",
          petType);
      DeviceEntity device = new DeviceEntity();

      // persist entities
      petType.persist();
      pet.persist();
      device.persist();

      defaultUser.addPet(pet);
      defaultUser.addDevice(device);

      defaultUser.persist();
    });

  }
}
