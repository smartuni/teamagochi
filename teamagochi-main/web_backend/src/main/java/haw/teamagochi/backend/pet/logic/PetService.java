package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;

public interface PetService {
  /**
   * Creates a pet and saves it persistently in the database.
   * Any future changes to the pet object's attributes will be updated automatically in the database.
   * @param name name for the pet
   * @param petType pet type of the pet
   * @return persisted pet object
   */
  PetEntity createPet(String name, PetTypeEntity petType);
}
