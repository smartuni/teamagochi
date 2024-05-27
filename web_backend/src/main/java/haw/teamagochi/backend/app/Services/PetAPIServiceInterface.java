package haw.teamagochi.backend.app.Services;

import haw.teamagochi.backend.app.Mapper.PetDTO;
import haw.teamagochi.backend.app.Mapper.PetInfoDTO;
import java.util.List;

public interface PetAPIServiceInterface {

  public PetDTO createPet(long userID, String petName, int petType);
  public PetDTO getPet(long userID, long petID);
  public List<PetInfoDTO> getPets(long userID);
  public boolean deletePet(long userID, long petID);
  public PetDTO changeDevice(long userID, long petID, long deviceID);


}
