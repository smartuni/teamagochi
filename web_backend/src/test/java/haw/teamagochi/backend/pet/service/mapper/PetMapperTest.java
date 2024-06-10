package haw.teamagochi.backend.pet.service.mapper;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PetMapperTest {

  PetMapper petMapper = PetMapper.MAPPER;

  @Test
  public void testPetMapper(){
    PetEntity pet = new PetEntity(new UserEntity(),"name", new PetTypeEntity("frog"));
    PetDTO petDTO = petMapper.toResource(pet);
    Assertions.assertEquals(petDTO.getPetID(), pet.getId());
    Assertions.assertEquals(petDTO.getPetName(), pet.getName());
    Assertions.assertEquals(petDTO.getPetTypeName(), pet.getPetType().getName());
    Assertions.assertEquals(petDTO.getFun(), pet.getFun());
    Assertions.assertEquals(petDTO.getXp(), pet.getXp());
  }

}
