package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import java.util.List;

public interface UcLeaderboard {

  /**
   * Ranked on Happiness and Wellbeing, two are equally ranked the name decides
   * @return the top 10 Pets
   */
  List<PetEntity> getTop10();

  /**
   * Ranked on Happiness and Wellbeing, two are equally ranked the name decides
   * @return the leaderboard consisting of all pets
   */
  List<PetEntity> getCompleteLeaderBoard();

  /**
   * Ranked purely on Happiness, when two are equally ranked the name decides
   * @return the top 10 pets according to their Happiness
   */
  List<PetEntity> getHappinessTop10();

  /**
   * Ranked purely on Happiness, when two are equally ranked the name decides
   * @return the leaderboard consisting of all pets
   */
  List<PetEntity> getCompleteHappinessLeaderBoard();

  /**
   * Ranked purely on Wellbeing, when two are equally ranked the name decides
   * @return the top 10 pets according to their Wellbeing
   */
  List<PetEntity> getWellbeingTop10();

  /**
   * Ranked purely on Wellbeing, when two are equally ranked the name decides
   * @return the leaderboard consisting of all pets
   */
  List<PetEntity> getCompleteWellbeingLeaderboard();



}
