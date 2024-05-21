package haw.teamagochi.backend.app.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
//ggf als abstrct class um custom mappings zu erstellen --> ermöglicht nutzen von methoden??!
@Mapper(componentModel="cdi")
public interface PetMapper {
  @Mapping(source = "name", target = "name")
  @Mapping(source)
  PetDTO toResource(Pet pet);


}
