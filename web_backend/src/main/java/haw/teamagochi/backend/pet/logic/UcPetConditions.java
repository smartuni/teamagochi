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
   * feeds a pet, Hunger is reversed: less == better
   * @param pet the pet to be fed
   * @return the pet that was fed
   */
  PetEntity decreaseHunger(PetEntity pet);

  /**
   * increase hunger, Hunger is reversed: more == worse
   * @param pet the pet to get more hungry
   * @return the pet that has the hunger updated/increased
   */
  PetEntity increaseHunger(PetEntity pet);

  /**
   * increases a pets health
   * @param pet the pet to give medicine to
   * @return the pet with updated health
   */
  PetEntity increaseHealth(PetEntity pet);

  /**
   * decreases the health value of a pet = pet gets less health(y)
   * @param pet the pet to get the health decreased
   * @return the pet with decreased health
   */
  PetEntity decreaseHealth(PetEntity pet);

  /**
   * cleanliness gets increased, cleans a pet
   * @param pet the pet to be cleaned
   * @return the pet that was cleaned
   */
  PetEntity increaseCleanliness(PetEntity pet);

  /**
   * decrease cleanliness, pet gets less clean(liness)
   * @param pet the pet to get the cleanliness updates
   * @return the pet with the updated cleanliness
   */
  PetEntity decreaseCleanliness(PetEntity pet);

  /**
   * increase the fun of a pet
   * @param pet the pet to be played with
   * @return the pet with increased fun
   */
  PetEntity increseFun(PetEntity pet);

  /**
   * pet gets fun decreased
   * @param pet the pet to get the fun updated
   * @return pet with updated (less) fun
   */
  PetEntity decreaseFun(PetEntity pet);

}
