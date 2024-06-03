package haw.teamagochi.backend.pet.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.dataaccess.repository.PetRepository;
import haw.teamagochi.backend.pet.dataaccess.repository.PetTypeRepository;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PetUseCaseImpl implements PetUseCase {

  @Inject
  PetRepository petRepository;

  @Inject
  PetTypeRepository petTypeRepository;

  @Inject
  DeviceRepository deviceRepository;

  @Inject
  UserRepository userRepository;

  public PetEntity createPet(long userID, String name, long petTypeID) {
    UserEntity user = userRepository.findById(userID);
    PetTypeEntity petType = petTypeRepository.findById(petTypeID);
    if (user == null|| petType == null ) throw new NullPointerException();

    PetEntity pet = new PetEntity(user, name, petType);
    petRepository.persist(pet);
    return pet;
  }

  public PetEntity getPet(long petID) {
    return petRepository.findById(petID); // Null if not found
  }


  public PetEntity getPet(String name) {
    return petRepository.findByName(name); // Null if not found
  }


  public boolean deletePet(long petID) {
    return petRepository.deleteById(petID);
  }

  //TODO: das hier testen!!
  public List<PetEntity> getPets(long userID) {
    return petRepository.findByOwner(userID);
  }

  public void changeDevice(long petID, long deviceID) {
    PetEntity pet = petRepository.findById(petID);
    DeviceEntity device = deviceRepository.findById(deviceID);

    if (pet == null|device ==null) throw new NullPointerException("Either device or pet not found in the database.");

    pet.setDevice(device);
  }
}
