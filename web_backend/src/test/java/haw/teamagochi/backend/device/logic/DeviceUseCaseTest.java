package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UserUseCase;
import haw.teamagochi.backend.user.logic.UserUseCaseImpl;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DeviceUseCaseTest {

  @Inject
  DeviceUseCaseImpl deviceUseCase;

  @Inject
  UserUseCaseImpl userUseCase;


  @AfterEach
  @Transactional
  public void afterEach() {
    deviceUseCase.deleteAll();
    userUseCase.deleteAll();
  }


  @Test
  @Transactional
  public void testGetDevicesFromUser() {
    UserEntity user1 = userUseCase.createUser(new UUID(1,1));
    UserEntity user2 = userUseCase.createUser(new UUID(2,1));

    DeviceEntity device1 = deviceUseCase.createDevice("d1", DeviceType.FROG);
    DeviceEntity device2 = deviceUseCase.createDevice("d2", DeviceType.FROG);
    DeviceEntity device3 = deviceUseCase.createDevice("d3", DeviceType.FROG);

    device1.setOwner(user1);
    device2.setOwner(user1);
    device3.setOwner(user2);

    List<DeviceEntity> devicesOfuser1 = deviceUseCase.getDevices(user1.getId());

    assert devicesOfuser1.contains(device1);
    assert devicesOfuser1.contains(device2);
    assert  ! devicesOfuser1.contains(device3);


  }
  /*
  @Test
  @Transactional
  public void testConstraint() {
    DeviceEntity device = deviceService.createDevice("name", DeviceType.FROG);
    Assertions.assertThrows(ConstraintViolationException.class, ()-> {
      device.setName(null);
    });

  }

   */
}
