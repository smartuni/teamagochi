package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

/**
  * Default implementation for {@link UcFindPet}.
  */
@ApplicationScoped
public class UcFindPetImpl implements UcFindPet {

  @Inject
  PetRepository petRepository;

  @Inject
  UcFindUser ucFindUser;

  /**
   * {@inheritDoc}
   */
  @Override
  public PetEntity find(long id) {
    return petRepository.findById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PetEntity find(String name) {
    return petRepository.findByName(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<PetEntity> findOptional(long id) {
    return petRepository.findByIdOptional(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PetEntity> findAll() {
    return petRepository.findAll().stream().toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PetEntity> findAllByUserId(long userId) {
    UserEntity user = ucFindUser.find(userId);

    if (user == null) {
      throw new NotFoundException("User not found in database.");
    }

    return petRepository.findByOwner(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PetEntity> findAllByExternalUserId(String uuid) {
    UserEntity user = ucFindUser.find(uuid);

    if (user == null) {
      throw new NotFoundException("User not found in database.");
    }

    return petRepository.findByOwner(user);
  }
}
