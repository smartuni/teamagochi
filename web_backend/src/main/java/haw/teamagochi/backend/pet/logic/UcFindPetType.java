package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;

/**
 * Operations to find pet types.
 */
public interface UcFindPetType {

  /**
   * Find pet type by id.
   *
   * @param id of the type
   * @return entity if found, otherwise null
   */
  PetTypeEntity find(long id);
}
