package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

/**
 * Operations on a teamagochi pet object (32769).
 *
 *  <p>See <a href="https://github.com/smartuni/teamagochi/tree/main/platform/data/objectmodels">
 *    Object model specifications</a>
 */
public interface UcPetResourceOperations {

  /**
   * Write a pet to the device.
   *
   * @param entity is the pet to write
   * @return true if successful, otherwise false
   */
  boolean writePet(String endpoint, PetEntity entity);

  /**
   * Observe interactions.
   *
   * @param endpoint name of the client device
   */
  void observePetInteractions(String endpoint);
}
