package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;

/**
 * Use Case for the Conitions of a pet like:
 *   hunger
 *   health
 *   cleanliness
 *   fun
 * and the corresponding computations
 */
public interface UcPetConditions {

  /**
   * feeds a pet
   * @param pet the pet to be fed
   * @return the pet that was fed
   */
  PetEntity feedPet(PetEntity pet);

  /**
   * updates the hunger of a pet = pet gets more hungry
   * @param pet the pet to get more hungry
   * @return the pet that has the hunger updated/increased
   */
  PetEntity hungerUpdate(PetEntity pet);

  /**
   * gives a pet medicine and increases its health
   * @param pet the pet to give medicine to
   * @return the pet with updated health
   */
  PetEntity medicatePet(PetEntity pet);

  /**
   * updates the health value of a pet = pet gets less health(y)
   * @param pet the pet to get the health decreased
   * @return the pet with decreased health
   */
  PetEntity healthUpdate(PetEntity pet);

  /**
   * cleans a pet = cleanliness gets increased
   * @param pet the pet to be cleaned
   * @return the pet that was cleaned
   */
  PetEntity cleanPet(PetEntity pet);

  /**
   * updates the cleanliness of a pet = pet gets less clean(liness)
   * @param pet the pet to get the cleanliness updates
   * @return the pet with the updated cleanliness
   */
  PetEntity cleanlinessUpdate(PetEntity pet);

  /**
   * play with a pet to increase fun = pet gets more fun
   * @param pet the pet to be played with
   * @return the pet with increased fun
   */
  PetEntity playWithPet(PetEntity pet);

  /**
   * updates the fun value of a pet = pet gets fun decreased
   * @param pet the pet to get the fun updated
   * @return pet with updated (less) fun
   */
  PetEntity updateFun(PetEntity pet);

}
