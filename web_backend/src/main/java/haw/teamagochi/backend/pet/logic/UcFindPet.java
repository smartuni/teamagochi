package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import java.util.List;

/**
 * Operations to find pets.
 */
public interface UcFindPet {

  /**
   * Find pet by id.
   *
   * @param id of the pet
   * @return entity if found, otherwise null
   */
  PetEntity find(long id);

  /**
   * Find pet by name.
   *
   * @param name of the pet
   * @return entity if found, otherwise null
   */
  PetEntity find(String name);

  /**
   * Find all pets by its owners id.
   *
   * @param userId of the user who owns the pet
   * @return entities if found, otherwise empty list
   */
  List<PetEntity> findByUserId(long userId);
}
