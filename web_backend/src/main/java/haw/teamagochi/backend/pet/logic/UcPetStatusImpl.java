package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;

public class UcPetStatusImpl implements UcPetStatus {
  @Override
  public PetEntity increaseHappiness(PetEntity pet, PetEvents event) {
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
      pet = increaseXP(pet, PetEvents.REACHED_MAX); // will add more xp than normal event
    }else if(happinessIncrease != 0){ //to check if xpIncrease is appropriate --> function call with right event
      pet.setHappiness(pet.getHappiness() + happinessIncrease);
      pet = increaseXP(pet, event); //add the corresponding xp
    }
    return pet;
  }//method

  @Override
  public PetEntity decreaseHappiness(PetEntity pet, PetEvents event) {
    //TODO ballancing
    int happinessDecrease;
    switch (event){
      case SICK -> happinessDecrease = 5;
      case BORED -> happinessDecrease = 15;
      case DIRTY -> happinessDecrease = 5;
      case HUNGRY -> happinessDecrease = 15;
      default -> happinessDecrease = 0;
    }
    if(pet.getHappiness() - happinessDecrease < 0){
      pet.setHappiness(0);
    }else if(happinessDecrease != 0){
      pet.setHappiness(pet.getHappiness() - happinessDecrease);
    }
    return pet;
  }

  @Override
  public PetEntity increaseWellbeing(PetEntity pet, PetEvents event) {
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
      pet = increaseXP(pet, PetEvents.REACHED_MAX); // will add more xp than normal event
    }else if(wellbeingIncrease != 0){ //to check if xpIncrease is appropriate --> function call with right event
      pet.setWellbeing(pet.getWellbeing() + wellbeingIncrease);
      pet = increaseXP(pet, event); //add the corresponding xp
    }
    return pet;
  }

  @Override
  public PetEntity decreaseWellbeing(PetEntity pet, PetEvents event) {
    //TODO ballancing
    int wellbeingDecrease;
    switch (event){
      case SICK -> wellbeingDecrease = 15;
      case BORED -> wellbeingDecrease = 5;
      case DIRTY -> wellbeingDecrease = 15;
      case HUNGRY -> wellbeingDecrease = 5;
      default -> wellbeingDecrease = 0;
    }
    if(pet.getWellbeing() - wellbeingDecrease < 0){
      pet.setWellbeing(0);
    }else if(wellbeingDecrease != 0){
      pet.setWellbeing(pet.getWellbeing() - wellbeingDecrease);
    }
    return pet;
  }

  @Override
  public PetEntity increaseXP(PetEntity pet, PetEvents event) {
    //TODO ballancing
    int xpIncrease;
    switch(event){
      case FEED -> xpIncrease = 10;
      case PLAY -> xpIncrease = 10; //duplicated due to possible reballancing
      case CLEAN -> xpIncrease = 10;
      case MEDICATE -> xpIncrease = 10;
      case REACHED_MAX -> xpIncrease = 25;
      default -> xpIncrease = 0;
    }
    pet.setXp(pet.getXp() + xpIncrease);
    return pet;
  }
}
