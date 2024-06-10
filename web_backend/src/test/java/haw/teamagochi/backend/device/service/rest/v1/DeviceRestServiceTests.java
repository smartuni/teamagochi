package haw.teamagochi.backend.device.service.rest.v1;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.logic.UcFindDeviceImpl;
import haw.teamagochi.backend.device.logic.UcManageDeviceImpl;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DeviceRestService}.
 */
@QuarkusTest
@TestHTTPEndpoint(DeviceRestService.class)
public class DeviceRestServiceTests {


  @Inject
  UcManageDeviceImpl deviceManager;
  @Inject
  UcFindDeviceImpl findDevice;
  // TODO implement the real test
  @Test
  public void testGetAllDevices() {
    when().get("/")
        .then()
        .statusCode(200)
        .body(is("[]"));
  }

  // TODO implement the real test
  @Test
  public void testGetDeviceById() {
    DeviceDTO result =
        when().get("/1")
          .then()
          .statusCode(200)
          .extract()
          .as(DeviceDTO.class);
    System.out.println(result.getDeviceID());
    System.out.println(result.getDeviceName());
  }

  // TODO implement the real test
  @Test
  public void testGetDeviceById_NotFound() {
    when()
        .get("/99")
        .then()
        .statusCode(404)
        .body(is(""));
  }
}
