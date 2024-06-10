package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.leshanclient.datatypes.events.AwakeDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.CoaplogDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.RegistrationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.UpdatedDto;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Default implementation of {@link UcHandleLeshanEvents}.
 */
@ApplicationScoped
public class UcHandleLeshanEventsImpl implements UcHandleLeshanEvents {

  @Override
  public void handleRegistration(RegistrationDto dto) {
    System.out.println("Registration: " + dto.registrationId);
  }

  @Override
  public void handleDeregistration(RegistrationDto dto) {
    System.out.println("Deregistration: " + dto.registrationId);
  }

  @Override
  public void handleUpdate(UpdatedDto dto) {
    System.out.println("Updated: " + dto.update.registrationId);
  }

  @Override
  public void handleSleeping(AwakeDto dto) {
    System.out.println("Sleeping: " + dto.ep);
  }

  @Override
  public void handleAwake(AwakeDto dto) {
    System.out.println("Awake: " + dto.ep);
  }

  @Override
  public void handleCoapLog(CoaplogDto dto) {
    System.out.println("CoapLog: " + dto.ep);
  }
}
