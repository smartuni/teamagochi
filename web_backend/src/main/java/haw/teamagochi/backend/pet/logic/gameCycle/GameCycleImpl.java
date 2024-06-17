package haw.teamagochi.backend.pet.logic.gameCycle;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;


public class GameCycleImpl implements GameCycle{
  @Inject
  PetRepository petRepository;

  @Override
  @Scheduled(every = "{GameCycleImpl.interval}")
  public void petGameCylce() {
      for(PetEntity e: petRepository.listAll()){
        petRepository.persist(e);
        //TODO: hungerUC, Happiness, XP... Persist? oder auto?
      }
  }


}
