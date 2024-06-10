package haw.teamagochi.backend.device.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
  * Tests for {@link UcManageDevice}.
  */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UcManageDeviceTests {

  @Inject
  UcManageDevice ucManageDevice;

  @Inject
  UcManageUser ucManageUser;

  @Inject
  DeviceRepository deviceRepository;

  @AfterEach
  @Transactional
  public void afterEach() {
    deviceRepository.deleteAll();
    ucManageUser.deleteAll();
  }

  @Test
  void testCreateDevice() {
    // Given
    String name = "the-device";
    DeviceType type = DeviceType.FROG;

    // When
    DeviceEntity entity = ucManageDevice.create(name, type);
    DeviceEntity fetchedEntity = deviceRepository.findById(entity.getId());

    // Then
    assertNotNull(fetchedEntity);
    assertEquals(entity.getName(), fetchedEntity.getName());
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
