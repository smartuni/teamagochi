package haw.teamagochi.backend.pet.service.rest.v1.model;

public class PetDTO {

  long petID; //mapping done
  int level; //mapping done
  int xp; //mapping done
  int levelProgress; //mapping done
  String petName; //mapping done
  int happiness; //mapping done
  int wellbeing; //mapping done
  int hunger; //mapping done
  int health; //mapping done
  int cleanliness; //mapping done
  int fun;
  String petTypeName;

  long deviceID;



  public PetDTO(int xp, String petName, long petID, int happiness, int wellbeing, int hunger, int cleanliness, int fun,
                String petTypeName, int level, int levelProgress, int health){
    this.level = level; //compute level(xp);
    this.levelProgress = levelProgress; //compute levelProg(xp)
    this.petName = petName; //mapping done
    this.petID = petID; //mapping done
    this.happiness = happiness; // mapping done
    this.wellbeing = wellbeing; //mapping done
    this.hunger = hunger; //mapping done
    this.cleanliness = cleanliness; //mapping done
    this.fun = fun; //mapping done
    this.petTypeName = petTypeName;
    this.health = health; //mapping done


  }
  //TODO
}
