package haw.teamagochi.backend.device.service.rest.v1;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.device.service.rest.v1.mapper.DeviceMapper;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the device component.
 */
@Path("/v1/devices")
@Tag(name = "devices", description = "Everything about devices.")
@SecurityRequirement(name = "SecurityScheme")
public class DeviceRestService {

  @Inject
  protected DeviceMapper deviceMapper;

  @Inject
  protected UcFindDevice ucFindDevice;

  @Inject
  protected UcManageDevice ucManageDevice;

  /**
   * Get all devices.
   *
   * @return a list of all {@link DeviceDTO DeviceDTOs}, possibly empty
   */
  @GET
  @Operation(summary = "Get all devices")
  @APIResponse(responseCode = "200")
  public List<DeviceDTO> getAllDevices() {
    List<DeviceEntity> entities = ucFindDevice.findAll();
    return deviceMapper.mapEntityToTransferObject(entities);
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
    DeviceEntity device = ucFindDevice.findOptional(deviceId).orElseThrow(NotFoundException::new);
    return deviceMapper.mapEntityToTransferObject(device);
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
  public DeviceDTO deleteDeviceById(@PathParam("deviceId") long deviceId) {
    DeviceEntity entity = ucFindDevice.find(deviceId);
    boolean wasDeleted = ucManageDevice.deleteById(deviceId);
    if (wasDeleted) {
      return deviceMapper.mapEntityToTransferObject(entity);
    }
    throw new NotFoundException();
  }
}
