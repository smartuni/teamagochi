package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.devicemanager.DeviceManager;
import haw.teamagochi.backend.device.logic.registrationmanager.RegistrationManager;
import haw.teamagochi.backend.leshanclient.datatypes.events.AwakeDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.CoaplogDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.RegistrationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.UpdatedDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/** Default implementation of {@link UcHandleLeshanEvents}. */
@ApplicationScoped
public class UcHandleLeshanEventsImpl implements UcHandleLeshanEvents {

  private static final Logger LOGGER = Logger.getLogger(UcHandleLeshanEventsImpl.class);

  @Inject
  RegistrationManager registrationManager;

  @Inject
  DeviceManager deviceManager;

  @Override
  public void handleRegistration(RegistrationDto dto) {
    LOGGER.debug("Received registration event: " + dto.endpoint + " (" + dto.registrationId + ")");

    if (deviceManager.hasDevice(dto.endpoint)) {
      deviceManager.enableDevice(dto.endpoint);
    } else {
      registrationManager.addClient(dto.endpoint);
    }
  }

  @Override
  public void handleDeregistration(RegistrationDto dto) {
    LOGGER.debug(
        "Received deregistration event: " + dto.endpoint + " (" + dto.registrationId + ")");

    if (deviceManager.hasDevice(dto.endpoint)) {
      deviceManager.disableDevice(dto.endpoint);
    }
  }

  @Override
  public void handleUpdate(UpdatedDto dto) {
    if (deviceManager.hasDevice(dto.registration.endpoint)) {
      deviceManager.enableDevice(dto.registration.endpoint);
    } else {
      registrationManager.addClient(dto.registration.endpoint);
    }

    LOGGER.debug(
        "Received update event: "
            + dto.registration.endpoint
            + "("
            + dto.update.registrationId
            + ")");
  }

  @Override
  public void handleSleeping(AwakeDto dto) {
    LOGGER.debug("Received sleeping event: " + dto.ep);

    if (deviceManager.hasDevice(dto.ep)) {
      deviceManager.disableDevice(dto.ep);
    }
  }

  @Override
  public void handleAwake(AwakeDto dto) {
    LOGGER.debug("Received awake event: " + dto.ep);

    if (deviceManager.hasDevice(dto.ep)) {
      deviceManager.enableDevice(dto.ep);
    }
  }

  @Override
  public void handleCoapLog(CoaplogDto dto) {
    LOGGER.debug("Received coap logging event: " + dto.ep);
  }
}
