package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.devicemanager.DeviceManager;
import haw.teamagochi.backend.device.logic.registrationmanager.RegistrationManager;
import haw.teamagochi.backend.leshanclient.datatypes.common.ResourceDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.AwakeDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.CoaplogDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.NotificationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.RegistrationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.UpdatedDto;
import haw.teamagochi.backend.pet.logic.petmanager.InteractionRecord;
import haw.teamagochi.backend.pet.logic.petmanager.PetManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/**
 * Default implementation of {@link UcHandleLeshanEvents}.
 */
@ApplicationScoped
public class UcHandleLeshanEventsImpl implements UcHandleLeshanEvents {

  private static final Logger LOGGER = Logger.getLogger(UcHandleLeshanEventsImpl.class);

  @Inject
  RegistrationManager registrationManager;

  @Inject
  DeviceManager deviceManager;

  @Inject
  PetManager petManager;

  @Override
  public void handleRegistration(RegistrationDto dto) {
    LOGGER.info("Received registration event: " + dto.endpoint + " (" + dto.registrationId + ")");

    if (deviceManager.contains(dto.endpoint)) {
      deviceManager.enableDevice(dto.endpoint);
    } else {
      registrationManager.addClient(dto.endpoint);
    }
  }

  @Override
  public void handleDeregistration(RegistrationDto dto) {
    LOGGER.info("Received deregistration event: " + dto.endpoint + " (" + dto.registrationId + ")");

    if (deviceManager.contains(dto.endpoint)) {
      deviceManager.disableDevice(dto.endpoint);
    }
  }

  @Override
  public void handleUpdate(UpdatedDto dto) {
    LOGGER.debug("Received update event: " + dto.registration.endpoint
        + " (registrationId: " + dto.update.registrationId + ")");

    if (deviceManager.contains(dto.registration.endpoint)) {
      deviceManager.enableDevice(dto.registration.endpoint);
    } else {
      registrationManager.addClient(dto.registration.endpoint);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleNotification(NotificationDto dto) {
    LOGGER.info("Received notification event: " + dto.ep + " (kind: " + dto.kind + ")");

    if (!deviceManager.contains(dto.ep)) {
      LOGGER.warn("Received notification event for inactive device: " + dto.ep);
      return;
    }

    Long petId = deviceManager.getActivePetByClientEndpointName(dto.ep);
    InteractionRecord interactionRecord = petManager.getCurrentInteraction(petId);

    if (petId == null || interactionRecord == null) {
      LOGGER.warn("Received notification event for inactive pet: " + dto.ep);
      return;
    }

    if (interactionRecord.isEvaluated()) {
      petManager.addInteraction(petId, new InteractionRecord());
      interactionRecord = petManager.getCurrentInteraction(petId);
    }

    ResourceDto resource = dto.val;
    switch (resource.id) {
      case 40 -> // feed
          interactionRecord.setFeed(Integer.valueOf((String) resource.value));
      case 41 -> // medicate
          interactionRecord.setMedicate(Integer.valueOf((String) resource.value));
      case 42 -> // play
          interactionRecord.setPlay(Integer.valueOf((String) resource.value));
      case 43 -> // clean
          interactionRecord.setClean(Integer.valueOf((String) resource.value));
      default -> { /* noop */ }
    }
  }

  @Override
  public void handleSleeping(AwakeDto dto) {
    LOGGER.debug("Received sleeping event: " + dto.ep);

    if (deviceManager.contains(dto.ep)) {
      deviceManager.disableDevice(dto.ep);
    }
  }

  @Override
  public void handleAwake(AwakeDto dto) {
    LOGGER.debug("Received awake event: " + dto.ep);

    if (deviceManager.contains(dto.ep)) {
      deviceManager.enableDevice(dto.ep);
    }
  }

  @Override
  public void handleCoapLog(CoaplogDto dto) {
    LOGGER.debug("Received coap logging event: " + dto.ep);
  }
}
