package haw.teamagochi.backend.device.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.junit.jupiter.api.BeforeEach;
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

  private DeviceEntity deviceZero;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    deviceZero = ucManageDevice.create("deviceZero", DeviceType.FROG);
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    deviceRepository.deleteAll();
    ucManageUser.deleteAll();
  }

  @Test
  void testCreate_GivenNameAndType_PersistsEntity() {
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

  @Test
  void testCreate_GivenEntity_PersistsEntity() {
    // Given
    DeviceEntity entity = new DeviceEntity("the-device", DeviceType.FROG);

    // When
    ucManageDevice.create(entity);
    DeviceEntity fetchedEntity = deviceRepository.findById(entity.getId());

    // Then
    assertNotNull(fetchedEntity);
    assertEquals(entity.getName(), fetchedEntity.getName());
  }

  @Test
  void testDelete_GivenExistingId_ReturnsTrue() {
    // Given
    long existingId = deviceZero.getId();

    // When
    boolean result = ucManageDevice.deleteById(existingId);

    // Then
    assertTrue(result);
  }

  @Test
  void testDelete_GivenNonExistingId_ReturnsFalse() {
    // Given
    long nonExistingId = 9999;

    // When
    boolean result = ucManageDevice.deleteById(nonExistingId);

    // Then
    assertFalse(result);
  }
}
