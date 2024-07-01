package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UcPetStatusImpl implements UcPetStatus {
  @Override
  public void increaseHappiness(PetEntity pet, PetEvents event) {
    if(pet == null) return;

    /*
      If e.g. pet is fed, but pet was not hungry, then status (happiness etc.) should not be improved.
     */
    int happinessIncrease = switch (event) { // TODO: Merlin approval?
      case FEED -> pet.getHunger() > 0 ? 15:0;
      case PLAY -> pet.getFun() < 100 ? 15:0;
      case CLEAN -> pet.getCleanliness() < 100 ? 5:0;
      case MEDICATE -> pet.getHealth() < 100? 5:0;
      default -> 0;
    }; //switch

    if(pet.getHappiness() + happinessIncrease > 100){
      pet.setHappiness(100);
      increaseXP(pet, PetEvents.REACHED_MAX_STATUS); // will add more xp than normal event
    }else if(happinessIncrease != 0){ //to check if xpIncrease is appropriate --> function call with right event
      pet.setHappiness(pet.getHappiness() + happinessIncrease);
    }
  }//method

  @Override
  public void decreaseHappiness(PetEntity pet) {
    if(pet == null) return;
    int happinessDecrease = checkHappinessLimits(pet.getFun());//
    happinessDecrease += checkHappinessLimits(100 - pet.getHunger()); // bring hunger to same scale as fun
    if(pet.getHappiness() + happinessDecrease < 0){
      pet.setHappiness(0);
    }else if(happinessDecrease != 0){
      pet.setHappiness(pet.getHappiness() + happinessDecrease);
    }
  }

  @Override
  public void increaseWellbeing(PetEntity pet, PetEvents event) {
    if(pet == null) return;
    /*
      If e.g. pet is fed, but pet was not hungry, then status (happiness etc.) should not be improved.
     */
    int wellbeingIncrease = switch (event) { // TODO: Merlin approval?
      case FEED -> pet.getHunger() > 0 ? 5:0;
      case PLAY -> pet.getFun() < 100 ? 5:0;
      case CLEAN -> pet.getCleanliness() < 100 ? 15:0;
      case MEDICATE -> pet.getHealth() < 100? 15:0;
      default -> 0;
    }; //switch

    if(pet.getWellbeing() + wellbeingIncrease > 100){
      pet.setWellbeing(100);
      increaseXP(pet, PetEvents.REACHED_MAX_STATUS); // will add more xp than normal event
    }else if(wellbeingIncrease != 0){ //to check if xpIncrease is appropriate --> function call with right event
      pet.setWellbeing(pet.getWellbeing() + wellbeingIncrease);
      //pet = increaseXP(pet, event); seperate call drom interactionsUC
    }
  }

  @Override
  public void decreaseWellbeing(PetEntity pet) {
    if(pet == null) return;
    int wellbeingDecrease = checkWellbeingLimits(pet.getHealth());
    wellbeingDecrease = wellbeingDecrease + checkWellbeingLimits(pet.getCleanliness());
    if(pet.getWellbeing() + wellbeingDecrease < 0){
      pet.setWellbeing(0);
    }else if(wellbeingDecrease != 0){
      pet.setWellbeing(pet.getWellbeing() + wellbeingDecrease);
    }
  }

  @Override
  public void increaseXP(PetEntity pet, PetEvents event) {
    if(pet == null) return;
    //TODO ballancing
    int xpIncrease;
    switch(event){ // TODO: XP can be increased even if pet is starving. Is that intentional?
      case FEED -> xpIncrease = (pet.getHunger() == 0) ? 20 : 0;
      case PLAY -> xpIncrease = (pet.getFun() == 100) ? 20 : 0; //duplicated due to possible reballancing
      case CLEAN -> xpIncrease = (pet.getCleanliness() == 100) ? 20 : 0;
      case MEDICATE -> xpIncrease = (pet.getHealth() == 100) ? 20 : 0;
      case REACHED_MAX_STATUS -> xpIncrease = 40;
      default -> xpIncrease = 0;
    }
    pet.setXp(pet.getXp() + xpIncrease);
  }

  private int checkWellbeingLimits(int healthOrCleanlinessValue){
    if(healthOrCleanlinessValue > 59){
      return 0;
    }else if(healthOrCleanlinessValue > 39){
      return -5;
    }else if(healthOrCleanlinessValue > 19){
      return -10;
    }else if(healthOrCleanlinessValue >= 1){
      return -15;
    }else{// healthOrCleanlinessValue == 0
      return -20;
    }
  }

  private int checkHappinessLimits(int hungerOrFunValue){
    if(hungerOrFunValue > 59){
      return 0;
    }else if(hungerOrFunValue > 39){
      return -5;
    }else if(hungerOrFunValue > 19){
      return -10;
    }else if(hungerOrFunValue >= 1){
      return -15;
    }else{// hungerOrFunValue == 0
      return -20;
    }
  }
}
