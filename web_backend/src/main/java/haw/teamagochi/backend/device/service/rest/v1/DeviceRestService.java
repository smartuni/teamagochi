package haw.teamagochi.backend.device.service.rest.v1;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.device.service.rest.v1.mapper.DeviceMapper;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.general.security.SecurityUtil;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.logic.gameCycle.GameCycleImpl;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Objects;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the device component.
 */
@Path("/v1/devices")
@Tag(name = "a) devices", description = "Everything about devices.")
@SecurityRequirement(name = "SecurityScheme")
public class DeviceRestService {
  @Inject
  SecurityIdentity identity;

  @Inject
  protected DeviceMapper deviceMapper;

  @Inject
  protected UcFindDevice ucFindDevice;

  @Inject
  protected UcManageDevice ucManageDevice;

  @Inject
  protected UcManagePet ucManagePet;

  @Inject
  protected GameCycleImpl gameCycle;


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
  @APIResponse(responseCode = "401", description = "Not Authorized")
  public DeviceDTO deleteDeviceById(@PathParam("deviceId") long deviceId) {
    String uuid = SecurityUtil.getExternalUserId(identity);
    DeviceEntity entity = ucFindDevice.find(deviceId);
    if (entity != null
        && entity.getOwner() != null
        && Objects.equals(entity.getOwner().getExternalID().toString(), uuid)) {//request authorised
      boolean wasDeleted = ucManageDevice.deleteById(deviceId);
      if (wasDeleted) {
        return deviceMapper.mapEntityToTransferObject(entity);
      }
      throw new NotFoundException();
    }
    throw new UnauthorizedException();
  }//method


  @DELETE
  @Path("/reset")
  @Operation(summary = "delete all Devices and Pets")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public List<DeviceDTO> deleteAllPet() {
    List<DeviceEntity> entities = ucFindDevice.findAll();
    gameCycle.setStopRequested(true);
    while (!gameCycle.isStopped()){
      //waiting for stopped
    }
    ucManageDevice.deleteAll();
    ucManagePet.deleteAll();
    gameCycle.setStopRequested(false);
    return deviceMapper.mapEntityToTransferObject(entities);
  }
}
