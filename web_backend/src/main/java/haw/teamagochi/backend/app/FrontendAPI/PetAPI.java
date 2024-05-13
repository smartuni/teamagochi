package haw.teamagochi.backend.app.FrontendAPI;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import java.util.List;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestQuery;

@Path("Pet")
public class PetAPI {
  /**
   *
   * @param petID ID of the Pet
   * @param userAuthToken the Authentification-Token of the User
   * @return PetInfos (toBeDefined --> eg. Stats, Status, Type...)
   *
   */
  @Path("{petID}")
  @GET
  public Pet getPetInfos(String petID, @HeaderParam("UserAuthToken") String userAuthToken){
    //TODO
    return null;
  }
  /**
   *
   * @param userAuthToken the Authentification-Token of the User
   * @return petList (toBeDefined --> what is needed --> ID, Name, Type, Looks...)
   *
   */
  @Path("all")
  @GET
  public List<Pet> getPetIDs(@HeaderParam("UserAuthToken") String userAuthToken){
    //TODO
    return null;
  }
  /**
   *
   * @param userAuthToken the Authentification-Token of the User
   * @param petInfos Infos for the Pet --> to be Defined --> Name, type, look,....
   * @return Pet --> the representation of the newly created pet
   *
   */
  @Path("create")
  @PUT
  public Pet createPet(@RestQuery List<String> petInfos, @HeaderParam("UserAuthToken") String userAuthToken){
    //TODO
    return null;
  }


}
