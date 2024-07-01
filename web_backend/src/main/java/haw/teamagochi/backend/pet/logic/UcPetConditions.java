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
   */
  void decreaseHunger(PetEntity pet);

  /**
   * increase hunger, Hunger is reversed: more == worse
   * @param pet the pet to get more hungry
   */
  void increaseHunger(PetEntity pet);

  /**
   * increases a pets health
   * @param pet the pet to give medicine to
   */
  void increaseHealth(PetEntity pet);

  /**
   * decreases the health value of a pet = pet gets less health(y)
   * @param pet the pet to get the health decreased
   */
  void decreaseHealth(PetEntity pet);

  /**
   * cleanliness gets increased, cleans a pet
   * @param pet the pet to be cleaned
   */
  void increaseCleanliness(PetEntity pet);

  /**
   * decrease cleanliness, pet gets less clean(liness)
   * @param pet the pet to get the cleanliness updates
   */
  void decreaseCleanliness(PetEntity pet);

  /**
   * increase the fun of a pet
   * @param pet the pet to be played with
   */
  void increaseFun(PetEntity pet);

  /**
   * pet gets fun decreased
   * @param pet the pet to get the fun updated
   */
  void decreaseFun(PetEntity pet);

}
