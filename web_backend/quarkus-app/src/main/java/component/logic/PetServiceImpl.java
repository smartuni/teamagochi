package component.logic;

import component.dataaccess.model.PetEntity;
import component.dataaccess.model.PetTypeEntity;
import component.dataaccess.model.UserEntity;
import component.dataaccess.repository.PetRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class PetServiceImpl {

  @Inject
  PetRepository petRepository;

  /**
   * Creates a pet and saves it persistently.
   * @param name Name of the pet
   * @param petType The pet type of the pet. Needs to have been persisted before calling this method.
   * @return A pet object with the given attributes.
   */
  public PetEntity createPet(String name, PetTypeEntity petType) {
    PetEntity pet = new PetEntity(name, petType);
    petRepository.persist(pet);
    return pet;
  }

}
