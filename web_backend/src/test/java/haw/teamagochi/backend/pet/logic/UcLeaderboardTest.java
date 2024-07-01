package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UcLeaderboardTest {

  @Inject
  PetRepository repo;
  @Inject
  UcLeaderboard leaderbaords;
  @Inject
  UcManagePetType ucManagePetType;
  @Inject
  UcManagePet petManger;
  @Inject
  UcManageUser userManger;
  @Inject UcFindPet findPet;

  HashMap<String, PetEntity> petEntities;

  @BeforeEach
  @Transactional
  void beforeEach() {
    petManger.deleteAll();
    UserEntity owner = userManger.create(UUID.randomUUID());
    PetTypeEntity petType = ucManagePetType.createPetType("Frog");

    petEntities = new HashMap<>();
    PetEntity pet1 = petManger.create(owner.getId(), "ABC", petType.getId());
    pet1.setHappiness(100);
    pet1.setWellbeing(100);

    PetEntity pet2 = petManger.create(owner.getId(), "Muffl", petType.getId());
    pet2.setHappiness(90);
    pet2.setWellbeing(90);

    PetEntity pet3 = petManger.create(owner.getId(), "BCD", petType.getId());
    pet3.setHappiness(100);
    pet3.setWellbeing(100);

    repo.persist(pet1);
    repo.persist(pet2);
    repo.persist(pet3);
    petEntities.put("pet1", pet1);
    petEntities.put("pet2", pet2);
    petEntities.put("pet3", pet3);

    int statsModifyer = 1;
    for (int i = 15; i >= 4; i--) {
      //add some more pets
      PetEntity tmpPet = petManger.create(owner.getId(), "petewbv" + i, petType.getId());
      tmpPet.setWellbeing(10 + statsModifyer*2);
      tmpPet.setHappiness(10 + statsModifyer * 2);
      repo.persist(tmpPet);
      petEntities.put("pet" + i, tmpPet);
      statsModifyer++;
    }
  }

  @Test
  void testGetTop10() {
    Assertions.assertEquals(15, petEntities.size());
    Assertions.assertEquals(15, findPet.findAll().size());
    List<PetEntity> leaderboard = leaderbaords.getTop10();
    Assertions.assertEquals(10, leaderboard.size());
    Assertions.assertEquals(petEntities.get("pet1"), leaderboard.get(0));
    Assertions.assertEquals(petEntities.get("pet3"), leaderboard.get(1));
    Assertions.assertEquals(petEntities.get("pet2"), leaderboard.get(2));
    for (int i = 4; i <= 10; i++) {
      Assertions.assertEquals(petEntities.get("pet" + i), leaderboard.get(i - 1));
    }
  }//method

  @Test
  void testgetCompleteLeaderBoard() {
    List<PetEntity> leaderboard = leaderbaords.getCompleteLeaderBoard();
    Assertions.assertEquals(15, leaderboard.size());
    Assertions.assertEquals(petEntities.get("pet1"), leaderboard.get(0));
    Assertions.assertEquals(petEntities.get("pet3"), leaderboard.get(1));
    Assertions.assertEquals(petEntities.get("pet2"), leaderboard.get(2));
    for (int i = 4; i <= 15; i++) {
      Assertions.assertEquals(petEntities.get("pet" + i), leaderboard.get(i - 1));
    }
  }//method

  @Test
  void testGetHappinessTop10() {
    Assertions.assertEquals(15, petEntities.size());
    Assertions.assertEquals(15, findPet.findAll().size());
    List<PetEntity> leaderboard = leaderbaords.getHappinessTop10();
    Assertions.assertEquals(10, leaderboard.size());
    Assertions.assertEquals(petEntities.get("pet1"), leaderboard.get(0));
    Assertions.assertEquals(petEntities.get("pet3"), leaderboard.get(1));
    Assertions.assertEquals(petEntities.get("pet2"), leaderboard.get(2));
    for (int i = 4; i <= 10; i++) {
      Assertions.assertEquals(petEntities.get("pet" + i), leaderboard.get(i - 1));
    }
  }//method

  @Test
  void testGetCompleteHappinessLeaderBoard() {
    List<PetEntity> leaderboard = leaderbaords.getCompleteHappinessLeaderBoard();
    Assertions.assertEquals(15, leaderboard.size());
    Assertions.assertEquals(petEntities.get("pet1"), leaderboard.get(0));
    Assertions.assertEquals(petEntities.get("pet3"), leaderboard.get(1));
    Assertions.assertEquals(petEntities.get("pet2"), leaderboard.get(2));
    for (int i = 4; i <= 15; i++) {
      Assertions.assertEquals(petEntities.get("pet" + i), leaderboard.get(i - 1));
    }
  }//method

  @Test
  void testGetWellbeingTop10() {
    Assertions.assertEquals(15, petEntities.size());
    Assertions.assertEquals(15, findPet.findAll().size());
    List<PetEntity> leaderboard = leaderbaords.getWellbeingTop10();
    Assertions.assertEquals(10, leaderboard.size());
    Assertions.assertEquals(petEntities.get("pet1"), leaderboard.get(0));
    Assertions.assertEquals(petEntities.get("pet3"), leaderboard.get(1));
    Assertions.assertEquals(petEntities.get("pet2"), leaderboard.get(2));
    for (int i = 4; i <= 10; i++) {
      Assertions.assertEquals(petEntities.get("pet" + i), leaderboard.get(i - 1));
    }
  }//method

  @Test
  void testGetCompleteWellbeingLeaderBoard() {
    List<PetEntity> leaderboard = leaderbaords.getCompleteWellbeingLeaderboard();
    Assertions.assertEquals(15, leaderboard.size());
    Assertions.assertEquals(petEntities.get("pet1"), leaderboard.get(0));
    Assertions.assertEquals(petEntities.get("pet3"), leaderboard.get(1));
    Assertions.assertEquals(petEntities.get("pet2"), leaderboard.get(2));
    for (int i = 4; i <= 15; i++) {
      Assertions.assertEquals(petEntities.get("pet" + i), leaderboard.get(i - 1));
    }
  }//method
}//class
