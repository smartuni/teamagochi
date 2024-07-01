package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.Events.PetEvents;

/**
 * Use Case for the Status-Values of a pet:
 *    Happiness
 *    Wellbeing
 *    XP
 */
public interface UcPetStatus {

  /**
   * increases the happiness of a Pet
   * @param event the event that triggered the increase
   * @param pet the pet to increase the happiness for
   */
  void increaseHappiness(PetEntity pet, PetEvents event);

  /**
   * decrease the happiness of a pet
   * @param pet the pet to decrease the happiness for
   */
  void decreaseHappiness(PetEntity pet);

  /**
   * increases the wellbeing of a Pet
   * @param event the event that triggered the increase
   * @param pet the pet to increase the wellbeing for
   */
  void increaseWellbeing(PetEntity pet, PetEvents event);

  /**
   * decrease the wellbeing of a pet
   * @param pet the pet to decrease the wellbeing for
   */
  void decreaseWellbeing(PetEntity pet);

  /**
   * Increase the XP of a pet
   * @param pet the pet to increase the XP for
   * @param event the event that lead to the increase
   */
  void increaseXP(PetEntity pet, PetEvents event);

}
