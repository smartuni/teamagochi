package haw.teamagochi.backend.device.service.rest.v1;

import haw.teamagochi.backend.device.service.rest.v1.mapper.DeviceMapper;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the device component.
 */
@Path("/v1/device")
@Tag(name = "device", description = "Everything about devices.")
public class DeviceRestService {

  @Inject
  protected DeviceMapper deviceMapper;

  /**
   * Get all devices.
   *
   * @return a list of all {@link DeviceDTO DeviceDTOs}, possibly empty
   */
  @GET
  @Operation(summary = "Get all devices")
  @APIResponse(responseCode = "200")
  public List<DeviceDTO> getDevices() {
    // TODO replace with real implementation
    return new ArrayList<>();
  }

  /**
   * Get a device by its id.
   *
   * @param deviceId of the device to get
   * @return the {@link DeviceDTO} if found
   * @throws NotFoundException if no device was found
   */
  @GET
  @Path("/{deviceId}")
  @Operation(summary = "Get a device by its id")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public DeviceDTO getDeviceById(@PathParam("deviceId") long deviceId) {
    // TODO replace with real implementation
    if (deviceId == 1) {
      return new DeviceDTO(deviceId, "mock-device");
    }
    throw new NotFoundException();
  }

  /**
   * Delete a device by its id.
   *
   * @param deviceId of the device to delete
   * @return the {@link DeviceDTO} if deleted
   * @throws NotFoundException if no device was found
   */
  @DELETE
  @Path("/{deviceId}")
  @Operation(summary = "Delete a device by its id")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public DeviceDTO deleteDevice(@PathParam("deviceId") long deviceId) {
    // TODO replace with real implementation
    if (deviceId == 1) {
      return new DeviceDTO(1, "mock-device");
    }
    throw new NotFoundException();
  }

  /**
   * Register a device using a registration code.
   *
   * @param registrationCode the registration code for a device
   * @return the {@link DeviceDTO} if registration code is valid
   * @throws NotFoundException if the registration code is not found
   */
  @POST
  @Path("/register/{registrationCode}")
  @Operation(summary = "Register a device using a registration code")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public DeviceDTO registerDevice(@PathParam("registrationCode") String registrationCode) {
    // TODO replace with real implementation
    if (registrationCode.equals("aaaaaa")) {
      return new DeviceDTO(1, "mock-device");
    }
    throw new NotFoundException();
  }
}
