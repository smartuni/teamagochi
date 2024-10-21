package haw.teamagochi.backend.device.service.rest.v1;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.logic.UcFindDevice;
import haw.teamagochi.backend.device.logic.UcManageDevice;
import haw.teamagochi.backend.device.service.rest.v1.mapper.DeviceMapper;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.general.security.SecurityUtil;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.user.logic.UcFindUser;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Objects;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Token owner centered rest interface for the device component.
 *
 * @apiNote all 'self' endpoints read the user id of the JWT token
 */
@Path("/v1/devices/self")
@Tag(name = "1) devices/self", description = "Everything about the devices of a user whose ID is read from the JWT token.")
@SecurityRequirement(name = "SecurityScheme")
public class DeviceRestSelfService {

  @Inject
  SecurityIdentity identity;

  @Inject
  protected DeviceMapper deviceMapper;

  @Inject
  protected UcFindUser ucFindUser;

  @Inject
  protected UcFindPet ucFindPet;

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
  @Operation(summary = "Get all devices owned by a given user")
  @APIResponse(responseCode = "200")
  public List<DeviceDTO> getAllDevices() {
    String uuid = SecurityUtil.getExternalUserId(identity);
    List<DeviceEntity> entities = ucFindDevice.findAllByExternalUserId(uuid);
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
    String uuid = SecurityUtil.getExternalUserId(identity);
    DeviceEntity device = ucFindDevice.find(deviceId);
    if (device != null
        && device.getOwner() != null
        && Objects.equals(device.getOwner().getExternalID().toString(), uuid)) {
      return deviceMapper.mapEntityToTransferObject(device);
    }
    throw new NotFoundException();
  }

  /**
   * Update a device.
   *
   * @param deviceId of the device to update
   * @return the {@link DeviceDTO} if deleted
   * @throws NotFoundException if no device was found
   */
  @PUT
  @Path("/{deviceId}")
  @Operation(summary = "Update a device")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  @Transactional
  public DeviceDTO updateDevice(@PathParam("deviceId") long deviceId, DeviceDTO dto) {
    String uuid = SecurityUtil.getExternalUserId(identity);
    if (dto.getOwnerId() != null
        && Objects.equals(dto.getOwnerId(), uuid)
        && Objects.equals(dto.getId(), deviceId)) {
      DeviceEntity entity = deviceMapper.mapTransferObjectToEntity(
          dto, ucFindUser, ucFindPet, ucFindDevice);
      ucManageDevice.update(entity);
      return deviceMapper.mapEntityToTransferObject(entity);
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
  public DeviceDTO deleteDeviceById(@PathParam("deviceId") long deviceId) {
    String uuid = SecurityUtil.getExternalUserId(identity);
    DeviceEntity entity = ucFindDevice.find(deviceId);
    if (entity.getOwner() != null
        && Objects.equals(uuid, entity.getOwner().getExternalID().toString())
        && ucManageDevice.deleteById(deviceId)) {
      return deviceMapper.mapEntityToTransferObject(entity);
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
  public DeviceDTO registerDevice(
      @PathParam("registrationCode") String registrationCode, DeviceDTO dto) {
    String uuid = SecurityUtil.getExternalUserId(identity);
    DeviceEntity entity =
        ucManageDevice.registerDevice(registrationCode, dto.getName(), dto.getType(), uuid);
    if (entity != null) {
      return deviceMapper.mapEntityToTransferObject(entity);
    }
    throw new NotFoundException();
  }
}
