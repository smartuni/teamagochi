package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

public class UcPetConditionsImpl implements UcPetConditions{
  @Override
  public void decreaseHunger(PetEntity pet) {
    int decrease = 10;
    if(pet.getHunger() - decrease > 0){
      pet.setHunger(pet.getHunger() - decrease);
    }else{
      pet.setHunger(0);
    }
  }

  @Override
  public void increaseHunger(PetEntity pet) {
    int increase = 5;
    if(pet.getHunger() + increase < 100){
      pet.setHunger(pet.getHunger() + increase);
    }else{
      pet.setHunger(100);
    }
  }

  @Override
  public void increaseHealth(PetEntity pet) {
    int increase = 10;
    if(pet.getHealth() + increase < 100){
      pet.setHealth(pet.getHealth() + increase);
    }else{
      pet.setHealth(100);
    }
  }

  @Override
  public void decreaseHealth(PetEntity pet) {
    int decrease = 5;
    if(pet.getHealth() - decrease > 0){
      pet.setHealth(pet.getHealth() - decrease);
    }else{
      pet.setHealth(0);
    }
  }

  @Override
  public void increaseCleanliness(PetEntity pet) {
    int increase = 10;
    if(pet.getCleanliness() + increase < 100){
      pet.setCleanliness(pet.getCleanliness() + increase);
    }else{
      pet.setCleanliness(100);
    }
  }

  @Override
  public void decreaseCleanliness(PetEntity pet) {
    int decrease = 5;
    if(pet.getCleanliness() - decrease > 0){
      pet.setCleanliness(pet.getCleanliness() - decrease);
    }else{
      pet.setCleanliness(0);
    }
  }

  @Override
  public void increaseFun(PetEntity pet) {
    int increase = 10;
    if(pet.getFun() + increase < 100){
      pet.setFun(pet.getFun() + increase);
    }else{
      pet.setFun(100);
    }
  }

  @Override
  public void decreaseFun(PetEntity pet) {
    int decrease = 5;
    if(pet.getFun() - decrease > 0){
      pet.setFun(pet.getFun() - decrease);
    }else{
      pet.setFun(0);
    }
  }
}
