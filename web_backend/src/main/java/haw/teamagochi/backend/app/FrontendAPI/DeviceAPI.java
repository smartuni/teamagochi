package haw.teamagochi.backend.app.FrontendAPI;
import haw.teamagochi.backend.app.Mapper.DeviceDTO;
import haw.teamagochi.backend.app.Mapper.DeviceMapper;
import haw.teamagochi.backend.app.Services.RegistrationManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;

//@RegisterRestClient //needed for oidc??
@Path("Device")
public class DeviceAPI {
//Map Struct --> Mapper --> entitys auf json
  @Inject
  DeviceMapper deviceMapper;
  RegistrationManager registrationManager; //how does quarkus load the API --> how to pass the registrationManager instance
  DeviceAPI(RegistrationManager registrationManager){
    this.registrationManager = registrationManager;
  }

  /**
   * @param userAuthToken --> the user to get the devices for
   * @return List of Devices from the given user --> toBeDefined --> what info is needed: Name,ID....
   */
  @GET
  public List<DeviceDTO> getDevices(@HeaderParam("Authorization") String userAuthToken){ //ID + Name
    //TODO -->
    return null;
  }

  /**
   * Is this even needed?
   * @param deviceID --> the device to get further Information for
   * @param userAuthToken --> the user
   * @return Device information --> toBeDefined --> what info is needed: Name,ID, Current Pet,....
   */
  @Path("{deviceID}")
  @GET
  public DeviceDTO getDevice(String deviceID, @HeaderParam("Authorization") String userAuthToken){ //ID + Name + Pet ....
    //TODO --> check if user is authorized, get Device from DB, create DeviceDTO
    return null;
  }

  /**
   *
   * @param registerCode the code displyed on the device
   * @param userAuthToken --> the user to get the devices for
   * @return the device Information if succesfull, else null
   */
  @Path("register/{registerCode}")
  @POST
  public DeviceDTO registerDevice(String registerCode, @HeaderParam("Authorization") String userAuthToken){ //--> name missing

  }

  //TODO --> Device give name;
}
