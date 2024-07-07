package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.Events.CleanlinessVO;
import haw.teamagochi.backend.pet.logic.Events.HealthVO;
import haw.teamagochi.backend.pet.logic.Events.HungerVO;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import java.util.UUID;

/**
 * Tests for {@link UcKillPet}.
 */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UcKillPetTests {

    @Inject
    UcKillPet ucKillPet;

    @Inject
    UcManageUser ucManageUser;

    @Inject
    UcFindUser ucFindUser;

    @Inject
    UcManagePetType ucManagePetType;

    @Inject
    UcFindPetType ucFindPetType;

    @Inject
    UcManagePet ucManagePet;

    @BeforeEach
    public void beforeEach() {
        ucManageUser.create(new UUID(0,0));
        ucManagePetType.createPetType("FROG");
    }

    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    CleanlinessVO cleanlinessVO;


    @AfterEach
    @Transactional
    public void afterEach() {
        ucManagePet.deleteAll();
        ucManagePetType.deleteAll();
        ucManageUser.deleteAll();
    }

    @Test
    public void killTest() {
        // Given
        UserEntity user = ucFindUser.findAll().getFirst();
        PetTypeEntity petType = ucFindPetType.findAll().getFirst();

        PetEntity livingPet = ucManagePet.create(user.getId(), "Karl", petType.getId());
        PetEntity starvedPet = ucManagePet.create(user.getId(), "Karl Jr.", petType.getId());
        PetEntity sickPet = ucManagePet.create(user.getId(), "Karl Jr. Jr.", petType.getId());
        PetEntity unhygienicPet = ucManagePet.create(user.getId(), "Karl Jr. Jr. Jr.", petType.getId());


        // Make some pets suffer
        livingPet.setHunger(hungerVO.getMin() + 1);
        starvedPet.setHunger(hungerVO.getMax());
        sickPet.setHealth(healthVO.getMin());
        unhygienicPet.setCleanliness(cleanlinessVO.getMin());

        // None should be dead yet
        Assertions.assertFalse(ucKillPet.isDead(livingPet));
        Assertions.assertFalse(ucKillPet.isDead(starvedPet));
        Assertions.assertFalse(ucKillPet.isDead(sickPet));
        Assertions.assertFalse(ucKillPet.isDead(unhygienicPet));

        // Kill them, if they deserve it
        Assertions.assertFalse(ucKillPet.killIfShouldDie(livingPet));
        Assertions.assertTrue(ucKillPet.killIfShouldDie(starvedPet));
        Assertions.assertTrue(ucKillPet.killIfShouldDie(sickPet));
        Assertions.assertTrue(ucKillPet.killIfShouldDie(unhygienicPet));

        // Survival of the fittest
        Assertions.assertFalse(ucKillPet.isDead(livingPet));
        Assertions.assertTrue(ucKillPet.isDead(starvedPet));
        Assertions.assertTrue(ucKillPet.isDead(sickPet));
        Assertions.assertTrue(ucKillPet.isDead(unhygienicPet));
    }




}
