package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.pet.dataaccess.repository.PetTypeRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

/**
  * Default implementation for {@link UcManagePet}.
  */
@ApplicationScoped
public class UcManagePetImpl implements UcManagePet {

  @Inject
  PetRepository petRepository;

  @Inject
  PetTypeRepository petTypeRepository;

  @Inject
  UcFindDevice ucFindDevice;

  @Inject
  UcFindUser ucFindUser;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public PetEntity create(long userId, String name, long petTypeId) {
    UserEntity user = ucFindUser.find(userId);
    PetTypeEntity petType = petTypeRepository.findById(petTypeId);

    if (user == null || petType == null) {
      throw new NotFoundException();
    }

    PetEntity pet = new PetEntity(user, name, petType);
    petRepository.persist(pet);

    return pet;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public PetEntity create(PetEntity entity) {
    petRepository.persist(entity);

    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Transactional
  @Override
  public boolean deleteById(long petId) {
    return petRepository.deleteById(petId);
  }

  /**
   * {@inheritDoc}
   */
  @Transactional
  @Override
  public void changeDevice(long petId, long deviceId) {
//    PetEntity pet = petRepository.findById(petId);
//    DeviceEntity device = ucFindDevice.find(deviceId);
//
//    if (pet == null | device == null) {
//      throw new NotFoundException("Either device or pet not found in the database.");
//    }
//
//    device.setPet(pet);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public void deleteAll() {
    petRepository.deleteAll();
  }
}
