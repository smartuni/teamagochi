package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PetTypeServiceImpl {

  @Inject
  PetTypeRepository petTypeRepository;

  public PetTypeEntity createPetType() {
    PetTypeEntity petType = new PetTypeEntity();
    petTypeRepository.persist(petType);
    return petType;
  }
}
