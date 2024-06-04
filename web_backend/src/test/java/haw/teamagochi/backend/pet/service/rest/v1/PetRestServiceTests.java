package haw.teamagochi.backend.pet.service.rest.v1;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetInfoDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PetRestService}.
 */
@QuarkusTest
@TestHTTPEndpoint(PetRestService.class)
public class PetRestServiceTests {

  // TODO implement the real test
  @Test
  public void testGetAllPets() {
    when().get("/")
        .then()
        .statusCode(200)
        .body(is("[]"));
  }

  // TODO implement the test
  @Test
  public void testCreatePet() {
    // when().post()
  }

  // TODO implement the real test
  @Test
  public void testGetPetById() {
    PetInfoDTO result =
        when().get("/1")
            .then()
            .statusCode(200)
            .extract()
            .as(PetInfoDTO.class);
  }

  // TODO implement the real test
  @Test
  public void testGetPetById_NotFound() {
    when()
        .get("/99")
        .then()
        .statusCode(404)
        .body(is(""));
  }

  // TODO implement the test
  @Test
  public void testDeletePetById() {}
}
