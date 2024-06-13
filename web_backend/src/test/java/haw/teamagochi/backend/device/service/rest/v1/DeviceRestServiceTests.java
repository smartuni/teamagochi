package haw.teamagochi.backend.device.service.rest.v1;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.UcManagePetType;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DeviceRestService}.
 */
@QuarkusTest
@TestHTTPEndpoint(DeviceRestService.class)
public class DeviceRestServiceTests {

  @Inject
  UcManageDevice ucManageDevice;

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcManagePetType ucManagePetType;

  private Map<String, DeviceEntity> deviceEntities;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    UserEntity owner = ucManageUser.create(UUID.randomUUID());
    PetTypeEntity petType = ucManagePetType.createPetType("Frog");
    PetEntity pet = ucManagePet.create(owner.getId(), "Fifi", petType.getId());

    DeviceEntity device1 = new DeviceEntity("device-1", DeviceType.FROG);
    device1.setOwner(owner);
    device1.setPet(pet);
    ucManageDevice.create(device1);

    DeviceEntity device2 = new DeviceEntity("device-2", DeviceType.FROG);
    ucManageDevice.create(device2);

    deviceEntities = new HashMap<>();
    deviceEntities.put("device-1", device1);
    deviceEntities.put("device-2", device2);
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    ucManagePet.deleteAll();
    ucManagePetType.deleteAll();
    ucManageDevice.deleteAll();
    ucManageUser.deleteAll();
    deviceEntities = null;
  }

  @Test
  public void testGetAllDevices() {
    when().get("/")
        .then()
        .statusCode(200)
        .body("size()", is(2));
  }

  @Test
  public void testGetDeviceById() {
    DeviceEntity entity = deviceEntities.get("device-1");
    long entityId = entity.getId();

    DeviceDTO resultDto =
        when().get("/" + entityId)
          .then()
          .statusCode(200)
          .extract()
          .as(DeviceDTO.class);

    assert entity.getOwner() != null;
    assert entity.getPet() != null;

    assertEquals(entity.getId(), resultDto.getId());
    assertEquals(entity.getName(), resultDto.getName());
    assertEquals(String.valueOf(entity.getOwner().getExternalID()), resultDto.getOwnerId());
    assertEquals(entity.getPet().getId(), resultDto.getPetId());
  }

  @Test
  public void testGetDeviceById_NotFound() {
    when()
        .get("/99")
        .then()
        .statusCode(404)
        .body(is(""));
  }
}
