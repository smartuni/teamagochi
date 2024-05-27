import component.dataaccess.model.DeviceEntity;
import component.dataaccess.model.PetEntity;
import component.dataaccess.model.PetTypeEntity;
import component.dataaccess.model.UserEntity;
import component.dataaccess.repository.DeviceRepository;
import component.dataaccess.repository.PetRepository;
import component.dataaccess.repository.PetTypeRepository;
import component.dataaccess.repository.UserRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UserRepositoryTest {

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

  @BeforeEach
  @Transactional
  public void beforeEach() {
    userRepository.deleteAll();
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
      petTypeRepository.persist(petType);
      petRepository.persist(pet);
      deviceRepository.persist(device);

      defaultUser.addPet(pet);
      defaultUser.addDevice(device);

      userRepository.persist(defaultUser);
    });

  }
}
