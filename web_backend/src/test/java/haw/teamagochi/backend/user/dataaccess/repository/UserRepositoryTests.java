package haw.teamagochi.backend.user.dataaccess.repository;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.pet.dataaccess.repository.PetTypeRepository;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UserRepositoryTests {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManageDevice ucManageDevice;

  @Inject
  UserRepository userRepository;

  @Inject
  PetTypeRepository petTypeRepository;

  @Inject
  PetRepository petRepository;

  @Inject
  DeviceRepository deviceRepository;

  private static UserEntity defaultUser;

  @BeforeAll
  public static void beforeAll() {
    UUID uuid = new UUID(1,1);
    defaultUser = new UserEntity(
        uuid
    );
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    petRepository.deleteAll();
    petTypeRepository.deleteAll();
    deviceRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @Transactional
  public void testRepositoryAccess() {
    Assertions.assertDoesNotThrow(() -> {
      userRepository.persist(defaultUser);

      // create entities
      PetTypeEntity petType = new PetTypeEntity();
      PetEntity pet = new PetEntity(
          defaultUser,
          "petname",
          //"wrong_color_syntax",
          petType);
      DeviceEntity device = new DeviceEntity();

      // persist entities

      petTypeRepository.persist(petType);
      petRepository.persist(pet);
      deviceRepository.persist(device);


    });

  }

  @Test
  @Transactional
  public void testHibernatePersistenceAcrossMethodCalls() {
    UUID uuid1 = new UUID(1,1);
    UUID uuid2 = new UUID(2,2);

    UserEntity user = ucManageUser.create(uuid1);
    user.setExternalID(uuid2);

    UserEntity loadedUser = userRepository.findById(user.getId());

    Assertions.assertEquals(user, loadedUser);
  }

  /*@Test
  @Transactional
  public void testListAttributePersistence() {
    UUID uuid1 = new UUID(1,1);

    UserEntity user = userService.createUser(uuid1);
    DeviceEntity device = ucManageDevice.createDevice("name", DeviceType.FROG);
    user.addDevice(device);

    UserEntity loadedUser = userRepository.findById(user.getId());


    Assertions.assertEquals(user.getDevices(), loadedUser.getDevices());
  }*/

}
