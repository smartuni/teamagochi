package haw.teamagochi.backend.pet.logic.comparator;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import java.util.Comparator;

public class PetSortByWellbeing implements Comparator<PetEntity> {


  @Override
  public int compare(PetEntity o1, PetEntity o2) {
    int res = o1.getWellbeing() - o2.getWellbeing();
    if(res == 0){
      return(o1.getName().compareTo(o2.getName()));
    } else if (res < 0) {
      return 1;
    }
    return -1;
  }//metthod

}
