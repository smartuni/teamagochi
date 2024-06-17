package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
  * Default implementation for {@link UcFindPetType}.
  */
@ApplicationScoped
public class UcFindPetTypeImpl implements UcFindPetType {

  @Inject
  PetTypeRepository petTypeRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public PetTypeEntity find(long id) {
    return petTypeRepository.findById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PetTypeEntity> findAll() {
    return petTypeRepository.findAll().stream().toList();
  }
}
