package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UcPetStatusImpl implements UcPetStatus {
  @Override
  public void increaseHappiness(PetEntity pet, PetEvents event) {
    //TODO ballancing
    int happinessIncrease;
    switch (event){
      case FEED -> happinessIncrease = 15;
      case PLAY -> happinessIncrease = 15;
      case CLEAN -> happinessIncrease = 5;
      case MEDICATE -> happinessIncrease = 5;
      default -> happinessIncrease = 0;
    }//switch
    if(pet.getHappiness() + happinessIncrease > 100){
      pet.setHappiness(100);
      increaseXP(pet, PetEvents.REACHED_MAX_STATUS); // will add more xp than normal event
    }else if(happinessIncrease != 0){ //to check if xpIncrease is appropriate --> function call with right event
      pet.setHappiness(pet.getHappiness() + happinessIncrease);
      //pet = increaseXP(pet, event); //seperate call from interactions UC
    }
  }//method

  @Override
  public void decreaseHappiness(PetEntity pet) {
    int happinessDecrease = checkHappinessLimits(pet.getFun());//
    happinessDecrease += checkHappinessLimits(pet.getHunger());
    if(pet.getHappiness() - happinessDecrease < 0){
      pet.setHappiness(0);
    }else if(happinessDecrease != 0){
      pet.setHappiness(pet.getHappiness() - happinessDecrease);
    }
  }

  @Override
  public void increaseWellbeing(PetEntity pet, PetEvents event) {
    //TODO ballancing
    int wellbeingIncrease;
    switch (event){
      case FEED -> wellbeingIncrease = 5;
      case PLAY -> wellbeingIncrease = 5;
      case CLEAN -> wellbeingIncrease = 15;
      case MEDICATE -> wellbeingIncrease = 15;
      default -> wellbeingIncrease = 0;
    }//switch
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
    int wellbeingDecrease = checkWellbeingLimits(pet.getHealth());
    wellbeingDecrease = wellbeingDecrease + checkWellbeingLimits(pet.getCleanliness());
    if(pet.getWellbeing() - wellbeingDecrease < 0){
      pet.setWellbeing(0);
    }else if(wellbeingDecrease != 0){
      pet.setWellbeing(pet.getWellbeing() - wellbeingDecrease);
    }
  }

  @Override
  public void increaseXP(PetEntity pet, PetEvents event) {
    //TODO ballancing
    int xpIncrease;
    switch(event){
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
