package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.leshanclient.datatypes.events.AwakeDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.CoaplogDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.RegistrationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.UpdatedDto;

/**
 * Act on server-sent events of the Leshan Server.
 */
public interface UcHandleLeshanEvents {
  void handleRegistration(RegistrationDto dto);

  void handleDeregistration(RegistrationDto dto);

  void handleUpdate(UpdatedDto dto);

  void handleSleeping(AwakeDto dto);

  void handleAwake(AwakeDto dto);

  void handleCoapLog(CoaplogDto dto);
}
