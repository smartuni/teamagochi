package haw.teamagochi.backend.pet.logic.game;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcDeviceResourceOperations;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcPetResourceOperations;
import haw.teamagochi.backend.device.logic.devicemanager.DeviceManager;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.game.events.CleanlinessVO;
import haw.teamagochi.backend.pet.logic.game.events.FunVO;
import haw.teamagochi.backend.pet.logic.game.events.HappinessVO;
import haw.teamagochi.backend.pet.logic.game.events.HealthVO;
import haw.teamagochi.backend.pet.logic.game.events.HungerVO;
import haw.teamagochi.backend.pet.logic.game.events.WellbeingVO;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Default implementation for {@link GameCycle}.
 */
@ApplicationScoped
public class GameCycleImpl implements GameCycle {

  @Inject
  UcFindDevice findDevice;

  @Inject
  PetMapper petMapper;

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

  @Inject
  UcDeviceResourceOperations ucDeviceResourceOperations;

  @Inject
  UcPetResourceOperations ucPetResourceOperations;

  @Inject
  DeviceManager deviceManager;

  @Setter
  private volatile boolean stopRequested = false;

  @Getter
  private volatile boolean stopped = false;

  @Override
  @Scheduled(every = "{GameCycle.interval}")
  @Transactional
  public void doWork() {
    if (stopRequested) {
      // Exit the method if stop is requested
      stopped = true;
      return;
    }
    stopped = false;

    List<Long> activeDevices = deviceManager.getActiveDevices();
    System.out.println("Active Devices: " + activeDevices); // TODO remove

    // Only pets currently on a device
    for (DeviceEntity device : findDevice.findAll()) {
      PetEntity pet = device.getPet();

      if (pet != null) {
        deteriorate(pet);

        if (deviceManager.getActiveDevices().contains(device.getId())) {
          ucPetResourceOperations.writePet(device.getIdentifier(), pet);
        }
      }
    }
  }

  @Transactional
  // Needs to be public bc. Transactional.
  public void deteriorate(PetEntity pet) {
    // Deteriorate base attributes
    int newHunger = hungerVO.deteriorate(pet.getHunger());
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
