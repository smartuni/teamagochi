package haw.teamagochi.backend.device.logic;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DeviceUseCaseTest {

  @Inject
  DeviceUseCase deviceUseCase;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    deviceUseCase.deleteAll();
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
