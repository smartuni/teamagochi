package haw.teamagochi.backend.pet.service.rest.v1.mapper;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//ggf als abstrct class um custom mappings zu erstellen --> ermöglicht nutzen von methoden??!
@Mapper(componentModel="cdi")
public interface PetMapper {
  @Mapping(source = "id", target = "PetID")
  @Mapping(target ="level", expression = "java(computeLevel(pet))")
  @Mapping(target ="levelProgress", expression = "java(computeProgress(pet))")
  PetDTO toResource(PetEntity pet);


  default int computeLevel(PetEntity pet){
    //int[] levelInfo = LevelingManager.computeLevel(pet.xp);
    //return levelInfo[0];
    //TODO
    return 0;

  }
  default int computeProgress(PetEntity pet){
    //int[] levelInfo = LevelingManager.computeLevel(pet.xp);
    //return levelInfo[1];
    //TODO
    return 0;
  }


}
