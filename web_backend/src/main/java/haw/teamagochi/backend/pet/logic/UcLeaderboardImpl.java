package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcLeaderboard;
import haw.teamagochi.backend.pet.logic.comparator.PetSortByHappiness;
import haw.teamagochi.backend.pet.logic.comparator.PetSortByWellbeing;
import haw.teamagochi.backend.pet.logic.comparator.PetSortByWellbeingAndHappiness;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UcLeaderboardImpl implements UcLeaderboard {

  @Inject
  UcFindPet findPet;

  PetSortByHappiness compHappiness = new PetSortByHappiness();
  PetSortByWellbeing compWellbeing = new PetSortByWellbeing();
  PetSortByWellbeingAndHappiness compDefault = new PetSortByWellbeingAndHappiness();

  @Override
  public List<PetEntity> getTop10() {
    List<PetEntity> leaderboard = getCompleteLeaderBoard();
    if(leaderboard.size()>10){
      return leaderboard.subList(0,9);
    }
    return leaderboard;
  }

  @Override
  public List<PetEntity> getCompleteLeaderBoard() {
    List<PetEntity> leaderboard = findPet.findAll();
    leaderboard.sort(compDefault);
    return leaderboard;
  }

  @Override
  public List<PetEntity> getHappinessTop10() {
    List<PetEntity> leaderboard = getCompleteHappinessLeaderBoard();
    if(leaderboard.size()>10){
      return leaderboard.subList(0,9);
    }
    return leaderboard;
  }

  @Override
  public List<PetEntity> getCompleteHappinessLeaderBoard() {
    List<PetEntity> leaderboard = findPet.findAll();
    leaderboard.sort(compHappiness);
    return leaderboard;
  }

  @Override
  public List<PetEntity> getWellbeingTop10() {
    List<PetEntity> leaderboard = getCompleteWellbeingLeaderboard();
    if(leaderboard.size()>10){
      return leaderboard.subList(0,9);
    }
    return leaderboard;
  }

  @Override
  public List<PetEntity> getCompleteWellbeingLeaderboard() {
    List<PetEntity> leaderboard = findPet.findAll();
    leaderboard.sort(compWellbeing);
    return leaderboard;
  }
}
