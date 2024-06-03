package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;

public interface PetTypeUseCase {

  /**
   * Creates a pet type and saves it persistently in the database.
   * Any future changes to the pet type object's attributes will be updated automatically in the database.
   * @param name name for the pet type
   * @return persisted pet type object
   */
  PetTypeEntity createPetType(String name);
}
