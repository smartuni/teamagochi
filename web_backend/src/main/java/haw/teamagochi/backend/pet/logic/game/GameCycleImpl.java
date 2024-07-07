package haw.teamagochi.backend.pet.logic.game;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcPetResourceOperations;
import haw.teamagochi.backend.device.logic.devicemanager.DeviceManager;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcPetInteractions;
import haw.teamagochi.backend.pet.logic.game.events.CleanlinessVO;
import haw.teamagochi.backend.pet.logic.game.events.FunVO;
import haw.teamagochi.backend.pet.logic.game.events.HappinessVO;
import haw.teamagochi.backend.pet.logic.game.events.HealthVO;
import haw.teamagochi.backend.pet.logic.game.events.HungerVO;
import haw.teamagochi.backend.pet.logic.game.events.WellbeingVO;
import haw.teamagochi.backend.pet.logic.petmanager.InteractionRecord;
import haw.teamagochi.backend.pet.logic.petmanager.PetManager;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetStateDTO;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import org.jboss.logging.Logger;

/**
 * Default implementation for {@link GameCycle}.
 */
@ApplicationScoped
public class GameCycleImpl implements GameCycle {

  private static final Logger LOGGER = Logger.getLogger(GameCycleImpl.class);

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
  UcPetInteractions ucPetInteractions;

  @Inject
  UcPetResourceOperations ucPetResourceOperations;

  @Inject
  DeviceManager deviceManager;

  @Inject
  PetManager petManager;

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

    // Only pets currently on a device
    for (DeviceEntity device : findDevice.findAll()) {
      PetEntity pet = device.getPet();

      if (pet != null) {
        processPetInteractions(pet, device.getIdentifier());
        deteriorate(pet);

        if (activeDevices.contains(device.getId())) {
          ucPetResourceOperations.writePet(device.getIdentifier(), pet);

          LOGGER.info("Write " + pet.getName() + " to device " + device.getIdentifier() + ".");
        }
      }
    }
  }

  private void processPetInteractions(PetEntity pet, String endpoint) {
    InteractionRecord interactionRecord = petManager.getCurrentInteraction(pet.getId());

    if (interactionRecord != null && !interactionRecord.isEvaluated()) {

      Consumer<String> logInteractionFn = (action) ->
          LOGGER.info(
              "Interaction with " + pet.getName() + " on device " + endpoint + ": " + action);

      if (interactionRecord.getClean() > 0) {
        ucPetInteractions.cleanPet(pet);
        interactionRecord.setClean(0);
        logInteractionFn.accept("clean");
      }
      if (interactionRecord.getFeed() > 0) {
        ucPetInteractions.feedPet(pet);
        interactionRecord.setFeed(0);
        logInteractionFn.accept("feed");
      }
      if (interactionRecord.getPlay() > 0) {
        ucPetInteractions.playWithPet(pet);
        interactionRecord.setPlay(0);
        logInteractionFn.accept("play");
      }
      if (interactionRecord.getMedicate() > 0) {
        ucPetInteractions.medicatePet(pet);
        interactionRecord.setMedicate(0);
        logInteractionFn.accept("medicate");
      }

      interactionRecord.setEvaluated(true);
    }
  }

  private void deteriorate(PetEntity pet) {
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
