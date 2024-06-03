package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.PetServiceImpl;
import haw.teamagochi.backend.pet.logic.PetTypeServiceImpl;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetInfoMapper;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetInfoDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import org.jboss.resteasy.reactive.RestQuery;

//@RegisterRestClient
@Path("Pet")
public class PetRestService {

  @Inject
  PetServiceImpl petService;
  @Inject
  PetTypeServiceImpl petTypeService;
  @Inject
  PetMapper petMapper;
  @Inject
  PetInfoMapper petInfoMapper;



  /**
   * @param petID ID of the Pet
   * @param userAuthToken the Authentification-Token of the User
   * @return PetInfos (toBeDefined --> eg. Stats, Status, Type...)
   *
   */
  @Path("{petID}")
  @GET
  public PetDTO getPetInfos(String petID, @HeaderParam("Authorization") String userAuthToken){ //all Infos
    PetEntity pet = null;//TODO --> petService.get(petID);
    PetDTO petReturn = petMapper.toResource(pet);
    //TODO
    return petReturn;
  }
  /**
   *
   * @param userAuthToken the Authentification-Token of the User
   * @return petList (toBeDefined --> what is needed --> ID, Name, Type, Looks...)
   *
   */
  @GET
  public List<PetInfoDTO> getPets(@HeaderParam("Authorization") String userAuthToken){ //Name + Type + (looks?)
    ArrayList<PetEntity> petList = null; //TODO --> petService.getAll(userID);
    ArrayList<PetInfoDTO> petDTOList = new ArrayList<>();
    /*for(PetEntity pet : petList){
      PetInfoDTO petInfoDTO = petInfoMapper.toResource(pet);
      petDTOList.add(petInfoDTO);
    }//for*/
    //TODO
    return null;
  }
  /**
   *
   * @param userAuthToken the Authentification-Token of the User
   * @param petName Name for the pet
   * @param petType type of the pet
   * @return Pet --> the representation of the newly created pet --> same as getPetInfos
   *
   */

  @POST
  public PetDTO createPet(@RestQuery String petName, @RestQuery String petType, @HeaderParam("Authorization") String userAuthToken){
    PetTypeEntity type = null; // TODO --> petTypeService.getEntity(petType)
    PetEntity createdPet = petService.createPet(petName, type);
    PetDTO petReturn = petMapper.toResource(createdPet);

    //TODO
    return petReturn;
  }

  /**
   *
   * @param petID ID of the pet to be deleted
   * @param userAuthToken user Authentification Token
   * @return true if succesfull, false if not succesfull
   */
  @DELETE
  public boolean deletePet(@RestQuery String petID, @HeaderParam("Authorization") String userAuthToken){
    //TODO
    return false;
  }


}
