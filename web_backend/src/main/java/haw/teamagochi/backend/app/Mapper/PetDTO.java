package haw.teamagochi.backend.app.Mapper;

public class PetDTO {

  private int level;
  private int levelProgress;
  private String name;
  private Long id;
  private int happiness;
  private int wellbeing;
  private int hunger;
  private int cleanliness;
  private int fun;
  private int petTypeID; //ggf. int oder byte --> wie wollen wir das lösen (haben wir eine bitmap
  // für die Grafik in der db oder wie?
  private String petTypeName;



  public PetDTO(int xp, String name, Long id, int happiness, int wellbeing, int hunger, int cleanliness, int fun,
                byte petType, String petTypeName){
    this.level = 0; //compute level(xp);
    this.levelProgress = 0; //compute levelProg(xp)
    this.name = name;
    this.id = id;
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
