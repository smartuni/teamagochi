package haw.teamagochi.backend.device.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    entity2.setName("device-1");
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
  void testExists() {
    // Given
    long entityId = deviceEntities.get("device-1").getId();

    // When
    boolean doesExist = ucFindDevice.exists(entityId);

    // Then
    assertTrue(doesExist);
  }

  @Transactional
  @Test
  void testFindById() {
    // Given
    DeviceEntity entity = new DeviceEntity();
    deviceRepository.persist(entity);

    // When
    DeviceEntity fetchedDevice = ucFindDevice.find(entity.getId());

    // Then
    assertEquals(entity.getId(), fetchedDevice.getId());
  }

  @Transactional
  @Test
  void testFindAllByUser() {
    // Given
    DeviceEntity entity = new DeviceEntity();
    entity.setName("device-3");
    entity.setOwner(user1);
    deviceRepository.persist(entity);

    // When
    List<DeviceEntity> fetchedByUser = ucFindDevice.findAllByUser(user1);
    List<DeviceEntity> fetchedByUserId = ucFindDevice.findAllByUserId(user1.getId());

    // Then
    assertEquals(2, fetchedByUser.size());
    assertEquals(2, fetchedByUserId.size());
  }

//
//  @Test
//  @Transactional
//  public void testGetDevicesFromUser() {
//    UserEntity user1 = ucManageUser.createUser(new UUID(1,1));
//    UserEntity user2 = ucManageUser.createUser(new UUID(2,1));
//
//    DeviceEntity device1 = ucManageDevice.createDevice("d1", DeviceType.FROG);
//    DeviceEntity device2 = ucManageDevice.createDevice("d2", DeviceType.FROG);
//    DeviceEntity device3 = ucManageDevice.createDevice("d3", DeviceType.FROG);
//
//    device1.setOwner(user1);
//    device2.setOwner(user1);
//    device3.setOwner(user2);
//
//    List<DeviceEntity> devicesOfuser1 = ucManageDevice.getDevices(user1.getId());
//
//    assert devicesOfuser1.contains(device1);
//    assert devicesOfuser1.contains(device2);
//    assert  ! devicesOfuser1.contains(device3);
//
//
//  }
//  /*
//  @Test
//  @Transactional
//  public void testConstraint() {
//    DeviceEntity device = deviceService.createDevice("name", DeviceType.FROG);
//    Assertions.assertThrows(ConstraintViolationException.class, ()-> {
//      device.setName(null);
//    });
//
//  }
//
//   */
}
