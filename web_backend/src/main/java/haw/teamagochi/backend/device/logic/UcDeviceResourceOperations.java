package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.clients.rest.DeviceStatus;

/**
 * Operations on a teamagochi device object (32770).
 *
 *  <p>See <a href="https://github.com/smartuni/teamagochi/tree/main/platform/data/objectmodels">
 *    Object model specifications</a>
 */
public interface UcDeviceResourceOperations {

  /**
   * Write a registration code to the device.
   *
   * @param endpoint to write to
   * @param status which should be written
   * @return true if successful, otherwise false
   */
  boolean writeStatus(String endpoint, DeviceStatus status);

  /**
   * Write a registration code to the device.
   *
   * @param endpoint to write to
   * @param registrationCode which should be written
   * @return true if successful, otherwise false
   */
  boolean writeRegistrationCode(String endpoint, String registrationCode);
}
