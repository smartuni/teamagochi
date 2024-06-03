package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetInfoDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;
import org.jboss.resteasy.reactive.RestQuery;

//@RegisterRestClient
@Path("Pet")
public class PetAPI {

  @Inject



  /**
   * @param petID ID of the Pet
   * @param userAuthToken the Authentification-Token of the User
   * @return PetInfos (toBeDefined --> eg. Stats, Status, Type...)
   *
   */
  @Path("{petID}")
  @GET
  public PetDTO getPetInfos(String petID, @HeaderParam("Authorization") String userAuthToken){ //all Infos
    //TODO
    return null;
  }
  /**
   *
   * @param userAuthToken the Authentification-Token of the User
   * @return petList (toBeDefined --> what is needed --> ID, Name, Type, Looks...)
   *
   */
  @GET
  public List<PetInfoDTO> getPets(@HeaderParam("Authorization") String userAuthToken){ //Name + Type + (looks?)
    //TODO
    return null;
  }
  /**
   *
   * @param userAuthToken the Authentification-Token of the User
   * @param petInfos Infos for the Pet --> to be Defined --> Name, type, look,....
   * @return Pet --> the representation of the newly created pet --> same as getPetInfos
   *
   */

  @POST
  public PetDTO createPet(@RestQuery List<String> petInfos, @HeaderParam("Authorization") String userAuthToken){
    //TODO
    return null;
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
