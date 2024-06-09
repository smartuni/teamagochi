package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import java.util.List;

public interface UcManagePet {
  /**
   * Creates a pet and saves it persistently in the database.
   * Any future changes to the pet object's attributes will be updated automatically in the database.

   * @return persisted pet object
   */
  PetEntity createPet(long userID, String name, long petTypeID);

  PetEntity getPet(long petID);
  PetEntity getPet(String name);

  boolean deletePet(long petID);

  List<PetEntity> getPets(long userID);

  void changeDevice(long petID, long deviceID);

  void deleteAll();


}
