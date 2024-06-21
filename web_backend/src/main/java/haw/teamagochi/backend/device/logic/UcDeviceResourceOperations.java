package haw.teamagochi.backend.device.logic;

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
   * @param registrationCode which should be written
   */
  boolean writeRegistrationCode(String endpoint, String registrationCode);
}
