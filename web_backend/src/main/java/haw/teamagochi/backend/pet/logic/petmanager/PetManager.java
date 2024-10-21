package haw.teamagochi.backend.pet.logic.petmanager;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

/**
 * Defines a pet manager.
 *
 * <p>Caches pets which are currently active and stores non-persistent computations.
 */
public interface PetManager {

  /**
   * Check if a pet is known to the manager.
   *
   * @param petId to check for
   * @return true if pet is known, otherwise false
   */
  boolean contains(Long petId);

  /**
   * Add a pet.
   *
   * @param pet entity which should be managed
   */
  void add(PetEntity pet);

  /**
   * Add an {@link InteractionRecord} for a given pet.
   *
   * @param petId which identifies the pet
   * @param record to be added
   */
  void addInteraction(Long petId, InteractionRecord record);

  /**
   * Get the last {@link InteractionRecord} for a given pet.
   *
   * @param petId which identifies the pet
   * @return the last record
   */
  InteractionRecord getLastInteraction(Long petId);

  /**
   * Get the last non-evaluated {@link InteractionRecord} for a given pet.
   *
   * @param petId which identifies the pet
   * @return the last record if it hasn't been evaluated, otherwise null
   */
  InteractionRecord getCurrentInteraction(Long petId);
}
