package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DeviceServiceTest {

  @Inject
  DeviceService deviceService;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    deviceService.deleteAll();
  }

  @Test
  @Transactional
  public void testConstraint() {
    DeviceEntity device = deviceService.createDevice("name", DeviceType.FROG);
    Assertions.assertThrows(ConstraintViolationException.class, ()-> {
      device.setName(null);
    });

  }
}
