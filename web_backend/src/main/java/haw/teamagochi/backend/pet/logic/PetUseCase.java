package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;

public interface PetUseCase {
  /**
   * Creates a pet and saves it persistently in the database.
   * Any future changes to the pet object's attributes will be updated automatically in the database.

   * @return persisted pet object
   */
  PetEntity createPet(long userID, String name, long petTypeID);
}
