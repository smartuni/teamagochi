package haw.teamagochi.backend.app.Mapper;

public class PetDTO {

  long petID; //mapping done
  int level; //mapping done
  int xp; //mapping done
  int levelProgress; //mapping done
  String name; //mapping done
  int happiness; //mapping done
  int wellbeing; //mapping done
  int hunger; //mapping done
  int health; //mapping done
  int cleanliness; //mapping done
  int fun;
  int petTypeID; //ggf. int oder byte --> wie wollen wir das lösen (haben wir eine bitmap
  // für die Grafik in der db oder wie?
  String petTypeName;

  long deviceID;



  public PetDTO(int xp, String name, Long id, int happiness, int wellbeing, int hunger, int cleanliness, int fun,
                byte petType, String petTypeName){
    this.level = 0; //compute level(xp);
    this.levelProgress = 0; //compute levelProg(xp)
    this.name = name;
    this.petID = id;
    this.happiness = happiness;
    this.wellbeing = wellbeing;
    this.hunger = hunger;
    this.cleanliness = cleanliness;
    this.fun = fun;
    this.petTypeID = petType;
    this.petTypeName = petTypeName;


  }
  //TODO
}
