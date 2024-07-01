package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.*;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
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

    @Inject
    PetMapper petMapper;

    @Inject
    WellbeingVO wellbeingVO;

    @Inject
    HappinessVO happinessVO;

    @Inject
    XpVO xpVO;


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
        PetStateDTO dto = petMapper.mapEntityToTransferObject(pet).getState();

        // Independent attributes
        pet.setHunger(hungerVO.dispatch(dto.getHunger(), event));
        pet.setHealth(healthVO.dispatch(dto.getHealth(), event));
        pet.setCleanliness(cleanlinessVO.dispatch(dto.getCleanliness(), event));
        pet.setFun(funVO.dispatch(dto.getFun(), event));

        // Attributes dependent on other attributes
        pet.setXp(xpVO.dispatch(event, dto));

        int newHappiness = happinessVO.dispatch(event, dto);
        pet.setHappiness(newHappiness);
        if (dto.getHappiness() != happinessVO.getMax() && newHappiness == happinessVO.getMax()) {
            pet.setXp(xpVO.dispatch(PetEvents.REACHED_MAX_STATUS, dto));
        }

        int newWellbeing = wellbeingVO.dispatch(event, dto);
        pet.setWellbeing(newWellbeing);
        if (dto.getWellbeing() != wellbeingVO.getMax() && newWellbeing== wellbeingVO.getMax()) {
            pet.setXp(xpVO.dispatch(PetEvents.REACHED_MAX_STATUS, dto));
        }

    }
}
