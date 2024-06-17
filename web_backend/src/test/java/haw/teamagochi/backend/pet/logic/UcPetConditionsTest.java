package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UcPetConditionsTest {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcManagePetType ucManagePetType;

  @Inject
  UcPetConditionsImpl conditions;

  private Map<String, PetEntity> petEntities;

  @BeforeEach
  @Transactional
  public void beforeEach() {
    UserEntity owner = ucManageUser.create(UUID.randomUUID());
    PetTypeEntity petType = ucManagePetType.createPetType("Frog");

    petEntities = new HashMap<>();
    PetEntity pet1 = ucManagePet.create(owner.getId(), "Fifi", petType.getId());
    pet1.setHappiness(0);
    pet1.setWellbeing(0);
    pet1.setHealth(90);
    pet1.setHunger(10);
    pet1.setCleanliness(90);
    pet1.setFun(90);
    pet1.setXp(0);

    PetEntity pet2 = ucManagePet.create(owner.getId(), "Muffl", petType.getId());
    pet2.setHappiness(90);
    pet2.setWellbeing(90);
    pet2.setHunger(90);
    pet2.setCleanliness(10);
    pet2.setFun(10);
    pet2.setHealth(10);

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
  void testDecreaseHunger() {
    assertDoesNotThrow(() -> {
      conditions.decreaseHunger(null);
    });
    Assertions.assertEquals(10, petEntities.get("pet1").getHunger() );
    conditions.decreaseHunger(petEntities.get("pet1"));
    Assertions.assertEquals(0, petEntities.get("pet1").getHunger() );
    conditions.decreaseHunger(petEntities.get("pet1"));
    Assertions.assertEquals(0, petEntities.get("pet1").getHunger());
    //next Test-Part
    Assertions.assertEquals(90, petEntities.get("pet2").getHunger());
    for (int i = 8; i >= 0; i--) {
      conditions.decreaseHunger(petEntities.get("pet2"));
      Assertions.assertEquals((i * 10), petEntities.get("pet2").getHunger());
    }

  }

  @Test
  void testIncreaseHunger(){
    assertDoesNotThrow(() -> {
      conditions.increaseHunger(null);
    });
    petEntities.get("pet2").setHunger(95);
    Assertions.assertEquals(95, petEntities.get("pet2").getHunger() );
    conditions.increaseHunger(petEntities.get("pet2"));
    Assertions.assertEquals(100, petEntities.get("pet2").getHunger());
    conditions.increaseHunger(petEntities.get("pet2"));
    Assertions.assertEquals(100, petEntities.get("pet2").getHunger());
    //next Test Part:
    Assertions.assertEquals(10, petEntities.get("pet1").getHunger() );
    for (int i = 1; i <= 9; i++) {
      conditions.increaseHunger(petEntities.get("pet1"));
      Assertions.assertEquals((10 + (5*i)), petEntities.get("pet1").getHunger());
    }
  }

  @Test
  void testIncreaseFun(){
    assertDoesNotThrow(() -> {
      conditions.increaseFun(null);
    });
    Assertions.assertEquals(90, petEntities.get("pet1").getFun() );
    conditions.increaseFun(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getFun() );
    conditions.increaseFun(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getFun());
    //next Test-Part
    Assertions.assertEquals(10, petEntities.get("pet2").getFun());
    for (int i = 2; i <= 10; i++) {
      conditions.increaseFun(petEntities.get("pet2"));
      Assertions.assertEquals((i * 10), petEntities.get("pet2").getFun());
    }
  }

  @Test
  void testDecreaseFun(){
    assertDoesNotThrow(() -> {
      conditions.decreaseFun(null);
    });
    Assertions.assertEquals(10, petEntities.get("pet2").getFun() );
    conditions.decreaseFun(petEntities.get("pet2"));
    Assertions.assertEquals(5, petEntities.get("pet2").getFun() );
    conditions.decreaseFun(petEntities.get("pet2"));
    Assertions.assertEquals(0, petEntities.get("pet2").getFun() );
    conditions.decreaseFun(petEntities.get("pet2"));
    Assertions.assertEquals(0, petEntities.get("pet2").getFun());
    //next Test-Part
    Assertions.assertEquals(90, petEntities.get("pet1").getFun());
    for (int i = 85; i <= 0; i -= 5) {
      conditions.decreaseFun(petEntities.get("pet1"));
      Assertions.assertEquals(i, petEntities.get("pet2").getFun());
    }
  }

  @Test
  void testIncreaseCleanliness(){
    assertDoesNotThrow(() -> {
      conditions.increaseCleanliness(null);
    });
    Assertions.assertEquals(90, petEntities.get("pet1").getCleanliness() );
    conditions.increaseCleanliness(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getCleanliness() );
    conditions.increaseCleanliness(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getCleanliness());
    //next Test-Part
    Assertions.assertEquals(10, petEntities.get("pet2").getCleanliness());
    for (int i = 2; i <= 10; i++) {
      conditions.increaseCleanliness(petEntities.get("pet2"));
      Assertions.assertEquals((i * 10), petEntities.get("pet2").getCleanliness());
    }
  }

  @Test
  void testDecreaseCleanliness(){
    assertDoesNotThrow(() -> {
      conditions.decreaseCleanliness(null);
    });
    Assertions.assertEquals(10, petEntities.get("pet2").getCleanliness() );
    conditions.decreaseCleanliness(petEntities.get("pet2"));
    Assertions.assertEquals(5, petEntities.get("pet2").getCleanliness() );
    conditions.decreaseCleanliness(petEntities.get("pet2"));
    Assertions.assertEquals(0, petEntities.get("pet2").getCleanliness() );
    conditions.decreaseCleanliness(petEntities.get("pet2"));
    Assertions.assertEquals(0, petEntities.get("pet2").getCleanliness());
    //next Test-Part
    Assertions.assertEquals(90, petEntities.get("pet1").getCleanliness());
    for (int i = 85; i <= 0; i -= 5) {
      conditions.decreaseCleanliness(petEntities.get("pet1"));
      Assertions.assertEquals(i, petEntities.get("pet2").getCleanliness());
    }
  }

  @Test
  void testIncreaseHealth(){
    assertDoesNotThrow(() -> {
      conditions.increaseHealth(null);
    });
    Assertions.assertEquals(90, petEntities.get("pet1").getHealth() );
    conditions.increaseHealth(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getHealth() );
    conditions.increaseHealth(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getHealth());
    //next Test-Part
    Assertions.assertEquals(10, petEntities.get("pet2").getHealth());
    for (int i = 2; i <= 10; i++) {
      conditions.increaseHealth(petEntities.get("pet2"));
      Assertions.assertEquals((i * 10), petEntities.get("pet2").getHealth());
    }
  }

  @Test
  void testDecreaseHealth(){
    assertDoesNotThrow(() -> {
      conditions.decreaseHealth(null);
    });
    Assertions.assertEquals(10, petEntities.get("pet2").getHealth() );
    conditions.decreaseHealth(petEntities.get("pet2"));
    Assertions.assertEquals(5, petEntities.get("pet2").getHealth() );
    conditions.decreaseHealth(petEntities.get("pet2"));
    Assertions.assertEquals(0, petEntities.get("pet2").getHealth() );
    conditions.decreaseHealth(petEntities.get("pet2"));
    Assertions.assertEquals(0, petEntities.get("pet2").getHealth());
    //next Test-Part
    Assertions.assertEquals(90, petEntities.get("pet1").getHealth());
    for (int i = 85; i <= 0; i -= 5) {
      conditions.decreaseHealth(petEntities.get("pet1"));
      Assertions.assertEquals(i, petEntities.get("pet2").getHealth());
    }
  }
}
