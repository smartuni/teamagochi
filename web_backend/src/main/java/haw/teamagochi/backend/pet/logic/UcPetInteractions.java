package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

/**
 * Use Case to handle interactions with the pet and handle their effects
 * Actions managed within this Use-Case:
 *    Feeding of the pet
 *    Cleaning of the pet
 *    Medicating the pet
 *    Playing with the pet
 */
public interface UcPetInteractions {


  /**
   * Feeds the pet
   * @param pet the pet to be fed
   */
  void feedPet(PetEntity pet);

  /**
   * Clean a pet
   * @param pet the pet to be cleaned
   */
  void cleanPet(PetEntity pet);

  /**
   * medicate a pet
   * @param pet the pet to be medicated
   * @return the pet that was medicated
   */
  void medicatePet(PetEntity pet);

  /**
   * play with a pet
   * @param pet the pet to be played with
   * @return the pet that has been played with
   */
  void playWithPet(PetEntity pet);


}
