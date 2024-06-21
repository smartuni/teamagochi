package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.general.security.SecurityUtil;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.pet.logic.UcFindPet;
import haw.teamagochi.backend.pet.logic.UcManagePet;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetDTO;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.logic.UcFindUser;
import haw.teamagochi.backend.user.logic.UcManageUser;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Objects;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the pet component.
 */
@Path("/v1/pets/self")
@Tag(name = "2) pets/self", description = "Everything about the pets of a user whose ID is read from the JWT token.")
@SecurityRequirement(name = "SecurityScheme")
public class PetRestSelfService {

  @Inject
  SecurityIdentity identity;

  @Inject
  PetMapper petMapper;

  @Inject
  protected UcFindPet ucFindPet;

  @Inject
  protected UcManagePet ucManagePet;

  @Inject
  protected UcFindUser ucFindUser;

  @Inject
  protected UcManageUser ucManageUser;

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
   * Create a pet.
   *
   * @param dto containing the pet data
   * @return the created pet
   */
  @POST
  @Operation(summary = "Create a pet")
  @APIResponse(responseCode = "200")
  public PetDTO createPet(PetDTO dto) {
    String uuid = SecurityUtil.getExternalUserId(identity);
    UserEntity owner = ucFindUser.find(uuid);
    if (owner == null) {
      ucManageUser.create(uuid); // create userId in database
    }
    dto.setOwnerId(uuid);
    PetEntity entity = petMapper.mapTransferObjectToEntity(dto);
    ucManagePet.create(entity);
    return petMapper.mapEntityToTransferObject(entity);
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
