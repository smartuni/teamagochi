package haw.teamagochi.backend.pet.logic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UcPetStatusImplTest {



  @Inject
  UcManageUser ucManageUser;

  @Inject
  UcManagePet ucManagePet;

  @Inject
  UcFindPet ucFindPet;

  @Inject
  UcManagePetType ucManagePetType;

  @Inject
  UcPetStatusImpl status;

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
    pet2.setHappiness(0);
    pet2.setWellbeing(90);
    pet2.setHunger(90);
    pet2.setCleanliness(10);
    pet2.setFun(10);
    pet2.setHealth(10);
    pet2.setXp(0);

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
  void testIncreaseHappiness(){
    Assertions.assertEquals(0, petEntities.get("pet1").getHappiness());
    Assertions.assertEquals(0, petEntities.get("pet1").getXp());
    //Test valid Params:
    for(int i = 1; i<=4; i++){
      if(i%2 == 0){
        status.increaseHappiness(petEntities.get("pet1"), PetEvents.FEED);
        Assertions.assertEquals((i*2*10-5), petEntities.get("pet1").getHappiness());
        status.increaseHappiness(petEntities.get("pet1"), PetEvents.CLEAN);
      }else{
        status.increaseHappiness(petEntities.get("pet1"), PetEvents.PLAY);
        Assertions.assertEquals((i*2*10-5), petEntities.get("pet1").getHappiness());
        status.increaseHappiness(petEntities.get("pet1"), PetEvents.MEDICATE);
      }//if
      Assertions.assertEquals((i*2*10), petEntities.get("pet1").getHappiness());
      Assertions.assertEquals(0, petEntities.get("pet1").getXp());
    }//for
    petEntities.get("pet1").setHappiness(100);
    status.increaseHappiness(petEntities.get("pet1"), PetEvents.PLAY);
    Assertions.assertEquals(100, petEntities.get("pet1").getHappiness());
    Assertions.assertEquals(40, petEntities.get("pet1").getXp());
    //Test invalid Params:
    Assertions.assertEquals(0, petEntities.get("pet2").getHappiness());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    status.increaseHappiness(petEntities.get("pet2"), PetEvents.DIRTY);
    Assertions.assertEquals(0, petEntities.get("pet2").getHappiness());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    status.increaseHappiness(petEntities.get("pet2"), PetEvents.BORED);
    Assertions.assertEquals(0, petEntities.get("pet2").getHappiness());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    status.increaseHappiness(petEntities.get("pet2"), PetEvents.REACHED_MAX_STATUS);
    Assertions.assertEquals(0, petEntities.get("pet2").getHappiness());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    assertDoesNotThrow(() -> {
      status.increaseHappiness(null, PetEvents.FEED);
    });

  }

  @Test
  void testDecreaseHappiness(){
    petEntities.get("pet1").setHappiness(100);
    petEntities.get("pet1").setHunger(0);
    petEntities.get("pet1").setFun(100);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setFun(60);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setHunger(40);//60
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setFun(40);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(95, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setHunger(60);//40
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(85, petEntities.get("pet1").getHappiness());// 2* -5
    petEntities.get("pet1").setHunger(40);//60
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(80, petEntities.get("pet1").getHappiness());
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(75, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setFun(20);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(65, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setFun(1);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(50, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setFun(0);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(30, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setFun(100);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(30, petEntities.get("pet1").getHappiness());
    petEntities.get("pet1").setCleanliness(0);
    petEntities.get("pet1").setHealth(0);
    status.decreaseHappiness(petEntities.get("pet1"));
    Assertions.assertEquals(30, petEntities.get("pet1").getHappiness());
    assertDoesNotThrow(() -> {
      status.decreaseHappiness(null);
    });
  }

  @Test
  void testIncreaseWellbeing(){
    Assertions.assertEquals(0, petEntities.get("pet1").getWellbeing());
    Assertions.assertEquals(0, petEntities.get("pet1").getXp());
    //Test valid Params:
    for(int i = 1; i<=4; i++){
      if(i%2 == 0){
        status.increaseWellbeing(petEntities.get("pet1"), PetEvents.MEDICATE);
        Assertions.assertEquals((i*2*10-5), petEntities.get("pet1").getWellbeing());
        status.increaseWellbeing(petEntities.get("pet1"), PetEvents.FEED);
      }else{
        status.increaseWellbeing(petEntities.get("pet1"), PetEvents.CLEAN);
        Assertions.assertEquals((i*2*10-5), petEntities.get("pet1").getWellbeing());
        status.increaseWellbeing(petEntities.get("pet1"), PetEvents.PLAY);
      }//if
      Assertions.assertEquals((i*2*10), petEntities.get("pet1").getWellbeing());
      Assertions.assertEquals(0, petEntities.get("pet1").getXp());
    }//for
    petEntities.get("pet1").setWellbeing(100);
    status.increaseWellbeing(petEntities.get("pet1"), PetEvents.MEDICATE);
    Assertions.assertEquals(100, petEntities.get("pet1").getWellbeing());
    Assertions.assertEquals(40, petEntities.get("pet1").getXp());
    //Test invalid Params:
    petEntities.get("pet2").setWellbeing(0);
    Assertions.assertEquals(0, petEntities.get("pet2").getWellbeing());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    status.increaseWellbeing(petEntities.get("pet2"), PetEvents.DIRTY);
    Assertions.assertEquals(0, petEntities.get("pet2").getWellbeing());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    status.increaseWellbeing(petEntities.get("pet2"), PetEvents.BORED);
    Assertions.assertEquals(0, petEntities.get("pet2").getWellbeing());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    status.increaseWellbeing(petEntities.get("pet2"), PetEvents.REACHED_MAX_STATUS);
    Assertions.assertEquals(0, petEntities.get("pet2").getWellbeing());
    Assertions.assertEquals(0, petEntities.get("pet2").getXp());
    assertDoesNotThrow(() -> {
      status.increaseWellbeing(null, PetEvents.FEED);
    });
  }

  @Test
  void testDecreaseWellbeing(){
    petEntities.get("pet1").setWellbeing(100);
    petEntities.get("pet1").setHealth(0);
    petEntities.get("pet1").setCleanliness(100);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHealth(60);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setCleanliness(60);//60
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(100, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHealth(40);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(95, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setCleanliness(40);//40
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(85, petEntities.get("pet1").getWellbeing());// 2* -5
    petEntities.get("pet1").setCleanliness(60);//60
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(80, petEntities.get("pet1").getWellbeing());
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(75, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHealth(20);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(65, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHealth(1);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(50, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHealth(0);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(30, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHealth(100);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(30, petEntities.get("pet1").getWellbeing());
    Assertions.assertEquals(30, petEntities.get("pet1").getWellbeing());
    petEntities.get("pet1").setHunger(100);
    petEntities.get("pet1").setFun(0);
    status.decreaseWellbeing(petEntities.get("pet1"));
    Assertions.assertEquals(30, petEntities.get("pet1").getWellbeing());
    assertDoesNotThrow(() -> {
      status.decreaseWellbeing(null);
    });
  }

  @Test
  void testIncreaseXP(){
    //TODO
    Assertions.assertTrue(true);
  }



}
