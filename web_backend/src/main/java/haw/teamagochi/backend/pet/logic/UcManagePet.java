package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

/**
 * Operations to manage pets.
 */
public interface UcManagePet {

  /**
   * Create a pet.
   *
   * @param userId of the pet owner
   * @param name of the pet
   * @param petTypeId of the pet
   * @return the created entity
   */
  PetEntity create(long userId, String name, long petTypeId);

  /**
   * Delete a pet.
   *
   * @param petId of the pet
   * @return TODO
   */
  boolean delete(long petId);

  /**
   * Change a pets assigned device.
   *
   * @param petId of the pet
   * @param deviceId of the assigned device
   */
  void changeDevice(long petId, long deviceId);

  /**
   * Delete all pets.
   */
  void deleteAll();
}
