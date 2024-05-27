package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;

public interface PetTypeService {

  PetTypeEntity createPetType(String name);
}
