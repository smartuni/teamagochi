package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetInfoMapper;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetInfoDTO;
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
 * Rest interface for the pet component.
 */
@Path("/v1/pet")
@Tag(name = "pet", description = "Everything about pets.")
public class PetRestService {

  @Inject
  PetMapper petMapper;

  @Inject
  PetInfoMapper petInfoMapper;

  /**
   * Get all pets.
   *
   * @return a list of all {@link PetInfoDTO PetInfoDTOs}, possibly empty
   */
  @GET
  @Operation(summary = "Get all pets")
  @APIResponse(responseCode = "200")
  public List<PetInfoDTO> getPets() {
    // TODO replace with real implementation
    return new ArrayList<>();
  }

  /**
   * Create a pet.
   *
   * @param dto containing the pet data
   * @return the created pet
   */
  @POST
  @Operation(summary = "Create a pet")
  @APIResponse(responseCode = "200")
  public PetInfoDTO createPet(PetInfoDTO dto) {
    // TODO replace with real implementation
    return dto;
  }

  /**
   * Get a pet by its id.
   *
   * @param petId of the pet to get
   * @return the {@link DeviceDTO} if found
   * @throws NotFoundException if no pet was found
   */
  @GET
  @Path("/{petId}")
  @Operation(summary = "Get a pet by its id")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public PetInfoDTO getPet(@PathParam("petId") long petId) {
    // TODO replace with real implementation, probably with PetDTO
    if (petId == 1) {
      return null;
      //return new PetInfoDTO();
    }
    throw new NotFoundException();
  }

  /**
   * Delete a pet by its id.
   *
   * @param petId of the pet to delete
   * @return the {@link PetDTO} if deleted
   * @throws NotFoundException if no device was found
   */
  @DELETE
  @Path("/{petId}")
  @Operation(summary = "Delete a pet by its id")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public PetInfoDTO deletePet(@PathParam("petId") long petId) {
    // TODO replace with real implementation
    if (petId == 1) {
      return null;//return new PetInfoDTO();
    }
    throw new NotFoundException();
  }
}
