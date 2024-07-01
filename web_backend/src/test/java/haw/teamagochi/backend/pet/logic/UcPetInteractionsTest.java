package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.Events.*;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
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
 * Tests for {@link UcPetInteractions}.
 */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UcPetInteractionsTest {

    /*
     *  Use Cases
     */

    @Inject
    UcPetInteractions ucPetInteractions;

    @Inject
    UcManageUser ucManageUser;

    @Inject
    UcFindUser ucFindUser;

    @Inject
    UcManagePet ucManagePet;

    @Inject
    UcManagePetType ucManagePetType;

    @Inject
    UcFindPetType ucFindPetType;


    /*
     *  Pet Attribute VOs
     */
    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    FunVO funVO;

    @Inject
    CleanlinessVO cleanlinessVO;

    @Inject
    PetMapper petMapper;

    @Inject
    WellbeingVO wellbeingVO;

    @Inject
    HappinessVO happinessVO;

    @Inject
    XpVO xpVO;

    @BeforeAll
    @Transactional
    public void beforeAll() {
        ucManageUser.create(new UUID(0,0));
        ucManagePetType.createPetType("FROG");
    }

    @AfterAll
    @Transactional
    public void afterAll() {
        ucManagePet.deleteAll();
        ucManagePetType.deleteAll();
        ucManageUser.deleteAll();

    }

    @Test
    public void testFeedPet() {
        // Given
        UserEntity user = ucFindUser.findAll().getFirst();
        PetTypeEntity petType = ucFindPetType.findAll().getFirst();
        PetEntity pet1 = ucManagePet.create(user.getId(), "Karl", petType.getId());
        PetEntity pet2 = ucManagePet.create(user.getId(), "Carlos", petType.getId());

        pet1.setHunger(hungerVO.getMin());
        int pet2HungerBefore = hungerVO.getMax()/2;
        pet2.setHunger(pet2HungerBefore);


        // When
        ucPetInteractions.feedPet(pet1);
        ucPetInteractions.feedPet(pet2);

        // Then
        Assertions.assertEquals (pet1.getHunger(), hungerVO.getMin());
        assert (pet2.getHunger() < pet2HungerBefore);

        //TODO: happiness etc.?


    }

}
