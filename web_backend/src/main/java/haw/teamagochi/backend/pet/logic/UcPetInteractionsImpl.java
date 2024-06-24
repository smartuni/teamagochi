package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UcPetInteractionsImpl implements UcPetInteractions{

    @Inject
    HungerVO hungerVO;

    @Inject
    HealthVO healthVO;

    @Inject
    FunVO funVO;

    @Inject
    CleanlinessVO cleanlinessVO;


    @Override
    public void feedPet(PetEntity pet) {
        handlePetEvent(pet, PetEvents.FEED);
    }

    @Override
    public void cleanPet(PetEntity pet) {
        handlePetEvent(pet, PetEvents.CLEAN);
    }

    @Override
    public void medicatePet(PetEntity pet) {
        handlePetEvent(pet, PetEvents.MEDICATE);
    }

    @Override
    public void playWithPet(PetEntity pet) {
        handlePetEvent(pet, PetEvents.PLAY);
    }

    private void handlePetEvent(PetEntity pet, PetEvents event) {
        pet.setHunger(hungerVO.dispatch(pet.getHunger(), event));
        pet.setHealth(healthVO.dispatch(pet.getHealth(), event));
        pet.setCleanliness(cleanlinessVO.dispatch(pet.getCleanliness(), event));
        pet.setFun(funVO.dispatch(pet.getFun(), event));
    }
}
