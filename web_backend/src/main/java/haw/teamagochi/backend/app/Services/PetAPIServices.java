package haw.teamagochi.backend.app.Services;

import haw.teamagochi.backend.app.Mapper.PetDTO;
import haw.teamagochi.backend.app.Mapper.PetInfoDTO;
import java.util.List;

public class PetAPIServices implements PetAPIServiceInterface{


  @Override
  public PetDTO createPet(long userID, String petName, int petType) {
    return null;
  }

  @Override
  public PetDTO getPet(long userID, long petID) {
    return null;
  }

  @Override
  public List<PetInfoDTO> getPets(long userID) {
    return null;
  }

  @Override
  public boolean deletePet(long userID, long petID) {
    return false;
  }

  @Override
  public PetDTO changeDevice(long userID, long petID, long deviceID) {
    return null;
  }
}
