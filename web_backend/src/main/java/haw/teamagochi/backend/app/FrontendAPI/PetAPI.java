package haw.teamagochi.backend.app.FrontendAPI;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import java.util.List;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestQuery;

@RegisterRestClient
@Path("Pet")
public class PetAPI {
  /**
   * @param userID --> keycloak
   * @param petID ID of the Pet
   * @param userAuthToken the Authentification-Token of the User
   * @return PetInfos (toBeDefined --> eg. Stats, Status, Type...)
   *
   */
  @Path("{petID}")
  @GET
  public Pet getPetInfos(String petID, @HeaderParam("Authorization") String userAuthToken){ //all Infos
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
  public List<Pet> getPetIDs(@HeaderParam("Authorization") String userAuthToken){ //Name + Type + (looks?)
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
  public Pet createPet(@RestQuery List<String> petInfos, @HeaderParam("Authorization") String userAuthToken){
    //TODO
    return null;
  }


}
