package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UcChangePetImpl implements UcChangePet{

  @Inject
  DeviceRepository deviceRepository;

  @Inject
  UcFindDevice ucFindDevice;
  @Inject
  UcFindPet findPet;

  @Override
  @Transactional
  public DeviceEntity changePet(long deviceId, long petId) {
    DeviceEntity device = ucFindDevice.find(deviceId);
    PetEntity pet = findPet.find(petId);
    if(device == null || pet == null){
      return null;
    }
    device.setPet(pet);
    //deviceRepository.persist(device); --> nicht da als @Transactional gekennzeichnet
    return device;
  }
}
