package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import java.util.List;
import java.util.Optional;

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
   * Find pet by id.
   *
   * @param id of the pet
   * @return the optional entity
   */
  Optional<PetEntity> findOptional(long id);

  /**
   * Find all pets.
   *
   * @return entities if found, otherwise empty list
   */
  List<PetEntity> findAll();

  /**
   * Find all pets by its owners id.
   *
   * @param userId of the user who owns the pet
   * @return entities if found, otherwise empty list
   */
  List<PetEntity> findAllByUserId(long userId);

  /**
   * Find pets by its owners system-wide id.
   *
   * @param uuid of the user who owns the pet.
   * @return entities if found, otherwise empty list
   */
  List<PetEntity> findAllByExternalUserId(String uuid);
}
