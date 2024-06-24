package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;
import haw.teamagochi.backend.pet.logic.UcPetConditionsImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

public class UcPetInteractionsOldVersion implements UcPetInteractions{

  @Inject
  UcPetConditionsImpl conditions;
  @Inject
  UcPetStatusImpl status;

  @Override
  public void feedPet(PetEntity pet) {
    conditions.decreaseHunger(pet);
    status.increaseHappiness(pet, PetEvents.FEED);
    status.increaseWellbeing(pet, PetEvents.FEED);
    status.increaseXP(pet, PetEvents.FEED);
  }

  @Override
  public void cleanPet(PetEntity pet) {
    conditions.increaseCleanliness(pet);
    status.increaseHappiness(pet, PetEvents.CLEAN);
    status.increaseWellbeing(pet, PetEvents.CLEAN);
    status.increaseXP(pet, PetEvents.CLEAN);
  }

  @Override
  public void medicatePet(PetEntity pet) {
    conditions.increaseHealth(pet);
    status.increaseHappiness(pet, PetEvents.MEDICATE);
    status.increaseWellbeing(pet, PetEvents.MEDICATE);
    status.increaseXP(pet, PetEvents.MEDICATE);
  }

  @Override
  public void playWithPet(PetEntity pet) {
    conditions.increaseFun(pet);
    status.increaseHappiness(pet, PetEvents.PLAY);
    status.increaseWellbeing(pet, PetEvents.PLAY);
    status.increaseXP(pet, PetEvents.PLAY);
  }
}
