package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PetServiceImpl implements PetService {

  @Inject
  PetRepository petRepository;

  /**
   * Creates a pet and saves it persistently.
   * @param name Name of the pet
   * @param petType The pet type of the pet. Needs to have been persisted before calling this method.
   * @return A pet object with the given attributes.
   */
  @Transactional
  public PetEntity createPet(String name, PetTypeEntity petType) {
    PetEntity pet = new PetEntity(name, petType);
    petRepository.persist(pet);
    return pet;
  }

}
