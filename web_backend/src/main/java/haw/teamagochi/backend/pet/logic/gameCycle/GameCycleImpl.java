package haw.teamagochi.backend.pet.logic.gameCycle;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.pet.logic.UcPetConditionsImpl;
import haw.teamagochi.backend.pet.logic.UcPetStatusImpl;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import java.util.Random;
import org.eclipse.microprofile.config.inject.ConfigProperty;


public class GameCycleImpl implements GameCycle{
  @Inject
  PetRepository petRepository;

  @Inject
  UcPetStatusImpl status;

  @Inject
  UcPetConditionsImpl conditions;

  @Override
  @Scheduled(every = "{GameCycleImpl.interval}")
  public void petGameCylce() {
    Random randomNum = new Random();
      for(PetEntity pet: petRepository.listAll()){
        conditions.increaseHunger(pet);
        conditions.decreaseFun(pet);
        conditions.decreaseCleanliness(pet);
        if(randomNum.nextInt(10)==1){ //health decrease is random based
          conditions.decreaseHealth(pet);
        }
        status.decreaseWellbeing(pet);
        status.decreaseHappiness(pet);
        petRepository.persist(pet);
      }
  }




}
