package component.logic;

import component.dataaccess.model.PetEntity;
import component.dataaccess.repository.PetRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ExampleService {

  @Inject
  PetRepository petRepository;

  @Transactional // @Transactional recommended for methods modifying the database
  public PetEntity createDefaultPet(String name) {
    PetEntity pet = new PetEntity();
    petRepository.persist(pet);
    return pet;
  }
}
