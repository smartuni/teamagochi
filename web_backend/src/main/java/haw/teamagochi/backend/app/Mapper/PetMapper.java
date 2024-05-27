package haw.teamagochi.backend.app.Mapper;

import haw.teamagochi.backend.app.Services.LevelingManager;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//ggf als abstrct class um custom mappings zu erstellen --> ermöglicht nutzen von methoden??!
@Mapper(componentModel="cdi")
public interface PetMapper {
  @Mapping(source = "id", target = "PetID")
  @Mapping(target ="level", expression = "java(computeLevel(pet))")
  @Mapping(target ="levelProgress", expression = "java(computeProgress(pet))")
  PetDTO toResource(Pet pet);


  default int computeLevel(Pet pet){
    int[] levelInfo = LevelingManager.computeLevel(pet.xp);
      return levelInfo[0];

  }
  default int computeProgress(Pet pet){
    int[] levelInfo = LevelingManager.computeLevel(pet.xp);
    return levelInfo[1];
  }


}
