package haw.teamagochi.backend.app.FrontendAPI;
import haw.teamagochi.backend.app.Mapper.DeviceDTO;
import haw.teamagochi.backend.app.Mapper.DeviceInfoDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import java.util.List;
import org.jboss.resteasy.reactive.RestCookie;
import org.jboss.resteasy.reactive.RestQuery;

//@RegisterRestClient //needed for oidc??
@Path("Device")
public class DeviceAPI {
//Map Struct --> Mapper --> entitys auf json

  /**
   * @param userAuthToken --> the user to get the devices for
   * @return List of Devices from the given user --> toBeDefined --> what info is needed: Name,ID....
   */
  @GET
  public List<DeviceInfoDTO> getDevices(@HeaderParam("Authorization") String userAuthToken){ //ID + Name
    //TODO
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
    //TODO
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
  public DeviceDTO registerDevice(String registerCode, @HeaderParam("Authorization") String userAuthToken){ //ID
    //TODO
    return null;
  }
}
