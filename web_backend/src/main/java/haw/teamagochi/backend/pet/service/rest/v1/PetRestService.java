package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the pet component.
 */
@Path("/v1/pets")
@Tag(name = "pets", description = "Everything about pets.")
public class PetRestService {

  @Inject
  PetMapper petMapper;

  @Inject
  protected UcFindPet ucFindPet;

  @Inject
  protected UcManagePet ucManagePet;

  /**
   * Get all pets.
   *
   * @return a list of all {@link PetDTO PetDTOs}, possibly empty
   */
  @GET
  @Operation(summary = "Get all pets")
  @APIResponse(responseCode = "200")
  public List<PetDTO> getAllPets() {
    List<PetEntity> entities = ucFindPet.findAll();
    return petMapper.mapEntityToTransferObject(entities);
  }

  /**
   * Get a pet by its id.
   *
   * @param petId of the pet to get
   * @return the {@link PetDTO} if found
   * @throws NotFoundException if no pet was found
   */
  @GET
  @Path("/{petId}")
  @Operation(summary = "Get a pet by its id")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "404", description = "Not Found")
  public PetDTO getPetById(@PathParam("petId") long petId) {
    PetEntity pet = ucFindPet.findOptional(petId).orElseThrow(NotFoundException::new);
    return petMapper.mapEntityToTransferObject(pet);
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
  public PetDTO deletePetById(@PathParam("petId") long petId) {
    PetEntity pet = ucFindPet.find(petId);
    boolean wasDeleted = ucManagePet.deleteById(petId);
    if (wasDeleted) {
      return petMapper.mapEntityToTransferObject(pet);
    }
    throw new NotFoundException();
  }
}
