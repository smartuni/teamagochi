package haw.teamagochi.backend.pet.logic.comparator;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import java.util.Comparator;

public class PetSortByWellbeingAndHappiness implements Comparator<PetEntity> {
  @Override
  public int compare(PetEntity o1, PetEntity o2) {
    int o1SumHappWell = o1.getWellbeing() + o1.getHappiness();
    int o2SumHappWell = o2.getWellbeing() + o2.getHappiness();
    int res = o1SumHappWell - o2SumHappWell;
    if(res == 0){
      return(o1.getName().compareTo(o2.getName()));
    } else if (res < 0) {
      return 1;
    }
    return -1;
  }//method

}
