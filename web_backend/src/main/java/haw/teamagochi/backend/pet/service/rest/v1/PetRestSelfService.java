package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.general.security.SecurityUtil;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Objects;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the pet component.
 */
@Path("/v1/pets/self")
@Tag(name = "pets/self", description = "Everything about a users pets.")
public class PetRestSelfService {

  @Inject SecurityIdentity identity;

  @Inject PetMapper petMapper;

  @Inject protected UcFindPet ucFindPet;

  /**
   * Get all pets.
   *
   * @return a list of all {@link PetDTO PetInfoDTOs}, possibly empty
   */
  @GET
  @Operation(summary = "Get all pets owned by a given user")
  @APIResponse(responseCode = "200")
  public List<PetDTO> getAllPets() {
    String uuid = SecurityUtil.getExternalUserId(identity);
    List<PetEntity> entities = ucFindPet.findAllByExternalUserId(uuid);
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
    String uuid = SecurityUtil.getExternalUserId(identity);
    PetEntity pet = ucFindPet.find(petId);
    if (pet != null
        && pet.getOwner() != null
        && Objects.equals(pet.getOwner().getExternalID().toString(), uuid)) {
      return petMapper.mapEntityToTransferObject(pet);
    }
    throw new NotFoundException();
  }
}
