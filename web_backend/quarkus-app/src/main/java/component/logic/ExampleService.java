package component.logic;

import component.dataaccess.model.PetEntity;
import component.dataaccess.model.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ExampleService {

  @Transactional // @Transactional recommended for methods modifying the database. You can also add it on top of the class.
  public PetEntity createDefaultPet(String name) {
    PetEntity pet = new PetEntity();
    pet.persist();
    return pet;
  }

  @Transactional
  public void updatePetName(Long petID, String newName) {
    PetEntity pet = PetEntity.findById(petID);
    pet.setName(newName);
    // pet.persist(); <-- once persisted, you don't need to explicitly save your entity: all modifications are automatically persisted on transaction commit.
  }
}
