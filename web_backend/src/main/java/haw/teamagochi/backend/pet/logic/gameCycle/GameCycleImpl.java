package haw.teamagochi.backend.pet.logic.gameCycle;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDeviceImpl;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.pet.logic.UcFindPetImpl;
import haw.teamagochi.backend.pet.logic.UcPetConditionsImpl;
import haw.teamagochi.backend.pet.logic.UcPetStatusImpl;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import java.util.Random;

import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;


public class GameCycleImpl implements GameCycle{

  @Inject
  PetRepository petRepository;
  @Inject
  UcFindDeviceImpl findDevice;

  @Inject
  UcPetStatusImpl status;

  @Inject
  UcPetConditionsImpl conditions;

  @Override
  @Scheduled(every = "{GameCycleImpl.interval}")
  @Transactional // One game cycle iteration is one transaction. After a transaction all changes to entities are saved automatically.
  public void petGameCycle() {
    Random randomNum = new Random();
      for(DeviceEntity device: findDevice.findAll()){//only pets currently on a device
        PetEntity pet = device.getPet();
        if(pet != null){
          conditions.increaseHunger(pet);
          conditions.decreaseFun(pet);
          conditions.decreaseCleanliness(pet);
          if(randomNum.nextInt(10)==1){ //health decrease is random based
            conditions.decreaseHealth(pet);
          }//if
          status.decreaseWellbeing(pet);
          status.decreaseHappiness(pet);
        }//if

      }//method
  }




}
