package haw.teamagochi.backend.pet.logic.gameCycle;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcFindDeviceImpl;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.pet.logic.*;
import haw.teamagochi.backend.pet.logic.Events.*;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import java.util.Random;

import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;


public class GameCycleImpl implements GameCycle{

  @Inject
  PetMapper petMapper;

  /*
    Use Cases
   */
  @Inject
  UcFindDevice findDevice;

  @Inject
  UcKillPet ucKillPet;

  /*
    Pet attribute VOs
   */

  @Inject
  HungerVO hungerVO;

  @Inject
  HealthVO healthVO;

  @Inject
  CleanlinessVO cleanlinessVO;

  @Inject
  FunVO funVO;


  @Inject
  WellbeingVO wellbeingVO;

  @Inject
  HappinessVO happinessVO;


  @Override
  @Scheduled(every = "{GameCycle.interval}")
  @Transactional
  public void petGameCycle() {
      for(DeviceEntity device: findDevice.findAll()){//only pets currently on a device
        PetEntity pet = device.getPet();
        if(pet != null && !ucKillPet.isDead(pet)) {
          deteriorate(pet);
          ucKillPet.killIfShouldDie(pet);
        }
      }//method
  }

  @Transactional
  // Needs to be public bc. Transactional.
  public void deteriorate(PetEntity pet) {
    // Deteriorate base attributes
    int newHunger = hungerVO.deteriorate(pet.getHunger()); // saved in var, for debugging
    pet.setHunger(newHunger);

    int newHealth = healthVO.deteriorate(pet.getHealth());
    pet.setHealth(newHealth);

    int newCleanliness = cleanlinessVO.deteriorate(pet.getCleanliness());
    pet.setCleanliness(newCleanliness);

    int newFun = funVO.deteriorate(pet.getFun());
    pet.setFun(newFun);

    // Deteriorate attributes dependent on other attributes
    PetStateDTO dto = petMapper.mapEntityToTransferObject(pet).getState();

    int newWellbeing = wellbeingVO.deteriorate(dto);
    pet.setWellbeing(newWellbeing);

    int newHappiness = happinessVO.deteriorate(dto);
    pet.setHappiness(newHappiness);
  }




}
