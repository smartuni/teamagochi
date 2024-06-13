package haw.teamagochi.backend.device.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link UcFindDevice}.
 */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UcFindDeviceTests {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcFindDevice ucFindDevice;

  @Inject
  UcManageDevice ucManageDevice;

  @Inject
  DeviceRepository deviceRepository;

  private Map<String, DeviceEntity> deviceEntities;

  private UserEntity user1;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    user1 = ucManageUser.create("2573f738-4484-8765-a985-c3214ed05f6f");

    DeviceEntity entity1 = new DeviceEntity();
    entity1.setName("device-1");
    entity1.setDeviceType(DeviceType.FROG);
    entity1.setOwner(user1);
    deviceRepository.persist(entity1);

    DeviceEntity entity2 = new DeviceEntity();
    entity2.setName("device-2");
    entity2.setDeviceType(DeviceType.FROG);
    deviceRepository.persist(entity2);

    deviceEntities = new HashMap<>();
    deviceEntities.put(entity1.getName(), entity1);
    deviceEntities.put(entity2.getName(), entity2);
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    deviceEntities = null;
    ucManageDevice.deleteAll();
    ucManageUser.deleteAll();
  }

  @Test
  void testExists_GivenExistingEntityId_ReturnsTrue() {
    // Given
    long entityId = deviceEntities.get("device-1").getId();

    // When
    boolean doesExist = ucFindDevice.exists(entityId);

    // Then
    assertTrue(doesExist);
  }

  @Test
  void testExists_GivenNonExistingEntityId_ReturnsFalse() {
    // Given
    long entityId = 9999;

    // When
    boolean doesExist = ucFindDevice.exists(entityId);

    // Then
    assertFalse(doesExist);
  }

  @Test
  @Transactional
  void testFind_GivenExistingId_ReturnsEntity() {
    // Given
    long entityId = deviceEntities.get("device-1").getId();

    // When
    DeviceEntity fetchedDevice = ucFindDevice.find(entityId);

    // Then
    assertEquals(fetchedDevice.getId(), entityId);
  }

  @Test
  @Transactional
  void testFind_GivenNonExistingId_ReturnsNull() {
    // Given
    long entityId = 9999;

    // When
    DeviceEntity fetchedDevice = ucFindDevice.find(entityId);

    // Then
    assertNull(fetchedDevice);
  }

  @Test
  @Transactional
  void testFindOptional_GivenExistingId_ReturnsOptional() {
    // Given
    long entityId = deviceEntities.get("device-1").getId();

    // WhenById
    Optional<DeviceEntity> fetchedDevice = ucFindDevice.findOptional(entityId);

    // Then
    assertTrue(fetchedDevice.isPresent());
    assertEquals(entityId, fetchedDevice.get().getId());
  }

  @Test
  @Transactional
  void testFindOptional_GivenNonExistingId_ReturnsEmptyOptional() {
    // Given
    long entityId = 9999;

    // When
    Optional<DeviceEntity> fetchedDevice = ucFindDevice.findOptional(entityId);

    // Then
    assertFalse(fetchedDevice.isPresent());
  }

  @Test
  void testFindAll_ReturnsAllEntities() {
    // When
    List<DeviceEntity> fetchedEntities = ucFindDevice.findAll();

    // Then
    assertEquals(deviceEntities.size(), fetchedEntities.size());
  }

  @Test
  @Transactional
  void testFindAllByUser_GivenValidArguments_ReturnsAllUserOwnedEntities() {
    // Given
    DeviceEntity entity = new DeviceEntity();
    entity.setName("device-3");
    entity.setOwner(user1);
    deviceRepository.persist(entity);
    String user1uuidString = String.valueOf(user1.getExternalID());

    // When
    List<DeviceEntity> fetchedByUser = ucFindDevice.findAllByUser(user1);
    List<DeviceEntity> fetchedByUserId = ucFindDevice.findAllByUserId(user1.getId());
    List<DeviceEntity> fetchedByExternalUserId =
        ucFindDevice.findAllByExternalUserId(user1uuidString);

    // Then
    assertEquals(2, fetchedByUser.size());
    assertEquals(2, fetchedByUserId.size());
    assertEquals(2, fetchedByExternalUserId.size());
  }
}
