package component.logic;

import component.dataaccess.model.DeviceEntity;
import component.dataaccess.model.PetTypeEntity;
import component.dataaccess.repository.DeviceRepository;
import component.dataaccess.repository.PetTypeRepository;
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
