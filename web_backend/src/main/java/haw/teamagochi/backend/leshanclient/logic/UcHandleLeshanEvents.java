package haw.teamagochi.backend.leshanclient.logic;

import haw.teamagochi.backend.leshanclient.sse.AwakeDto;
import haw.teamagochi.backend.leshanclient.sse.CoaplogDto;
import haw.teamagochi.backend.leshanclient.sse.RegistrationDto;
import haw.teamagochi.backend.leshanclient.sse.UpdatedDto;

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
