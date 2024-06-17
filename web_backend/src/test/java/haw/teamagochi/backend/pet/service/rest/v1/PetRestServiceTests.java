package haw.teamagochi.backend.pet.service.rest.v1;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.UcManagePetType;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
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
 * Tests for {@link PetRestService}.
 */
@QuarkusTest
@TestHTTPEndpoint(PetRestService.class)
public class PetRestServiceTests {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcFindPet ucFindPet;

  @Inject
  UcManagePetType ucManagePetType;

  private Map<String, PetEntity> petEntities;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    UserEntity owner = ucManageUser.create(UUID.randomUUID());
    PetTypeEntity petType = ucManagePetType.createPetType("Frog");

    petEntities = new HashMap<>();
    PetEntity pet1 = ucManagePet.create(owner.getId(), "Fifi", petType.getId());
    pet1.setHappiness(10);
    pet1.setWellbeing(11);
    pet1.setHealth(12);
    pet1.setHunger(13);
    pet1.setCleanliness(14);
    pet1.setFun(15);
    pet1.setXp(16);

    PetEntity pet2 = ucManagePet.create(owner.getId(), "Muffl", petType.getId());

    petEntities.put("pet1", pet1);
    petEntities.put("pet2", pet2);
  }

  @AfterEach
  @Transactional
  public void afterEach() {
    ucManagePet.deleteAll();
    ucManagePetType.deleteAll();
    ucManageUser.deleteAll();
    petEntities = null;
  }

  @Test
  public void testGetAllPets() {
    when().get("/")
        .then()
        .statusCode(200)
        .body("size()", is(2));
  }

  // TODO implement the test
  @Test
  public void testCreatePet() {
    // when().post()
  }

  @Test
  public void testGetPetById() {
    PetEntity entity = petEntities.get("pet1");
    long id = entity.getId();

    PetDTO resultDto =
        when().get("/" + id)
            .then()
            .statusCode(200)
            .extract()
            .as(PetDTO.class);

    assertEquals(entity.getId(), resultDto.getId());
    assertEquals(entity.getName(), resultDto.getName());
    assertEquals(entity.getPetType().getId(), resultDto.getType());
    // TODO pet owner
    //assertEquals(String.valueOf(entity.getOwner().getExternalID()), resultDto);

    assertEquals(entity.getHappiness(), resultDto.getState().getHappiness());
    assertEquals(entity.getWellbeing(), resultDto.getState().getWellbeing());
    assertEquals(entity.getHealth(), resultDto.getState().getHealth());
    assertEquals(entity.getHunger(), resultDto.getState().getHunger());
    assertEquals(entity.getCleanliness(), resultDto.getState().getCleanliness());
    assertEquals(entity.getFun(), resultDto.getState().getFun());
    assertEquals(entity.getXp(), resultDto.getState().getXp());
  }

  @Test
  public void testGetPetById_NotFound() {
    when()
        .get("/99")
        .then()
        .statusCode(404)
        .body(is(""));
  }

  @Test
  public void testDeletePetById() {
    int beforeSize = ucFindPet.findAll().size();

    when()
        .delete("/" + petEntities.get("pet1").getId())
        .then()
        .statusCode(200)
        .body(is(not("")));

    int afterSize = ucFindPet.findAll().size();

    assertEquals(beforeSize - 1, afterSize);
  }
}
