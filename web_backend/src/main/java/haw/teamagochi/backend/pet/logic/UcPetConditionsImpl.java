package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static java.lang.Math.max;
import static java.lang.Math.min;

@ApplicationScoped
public class UcPetConditionsImpl implements UcPetConditions{

  @Override
  public void decreaseHunger(PetEntity pet) {
    if(pet == null) return;
    int decrease = 10;
    pet.setHunger(max(0, pet.getHunger() - decrease));
  }

  @Override
  public void increaseHunger(PetEntity pet) {
    if(pet == null) return;
    int increase = 5;
    pet.setHunger(min(100, pet.getHunger() + increase));
  }

  @Override
  public void increaseHealth(PetEntity pet) {
    if(pet == null) return;
    int increase = 10;
    pet.setHealth(min(100, pet.getHealth() + increase));
  }

  @Override
  public void decreaseHealth(PetEntity pet) {
    if(pet == null) return;
    int decrease = 5;
    pet.setHealth(max(0, pet.getHealth() - decrease));
  }

  @Override
  public void increaseCleanliness(PetEntity pet) {
    if(pet == null) return;
    int increase = 10;
    pet.setCleanliness(min(100, pet.getCleanliness() + increase));
  }

  @Override
  public void decreaseCleanliness(PetEntity pet) {
    if(pet == null) return;
    int decrease = 5;
    pet.setCleanliness(max(0, pet.getCleanliness() - decrease ));
  }

  @Override
  public void increaseFun(PetEntity pet) {
    if(pet == null) return;
    int increase = 10;
    pet.setFun(min(100, pet.getFun() + increase));
  }

  @Override
  public void decreaseFun(PetEntity pet) {
    if(pet == null) return;
    int decrease = 5;
    pet.setFun(max(0, pet.getFun() - decrease));
  }
}
