package haw.teamagochi.backend.pet.service.rest.v1;

import haw.teamagochi.backend.pet.dataaccess.model.PetTypeEntity;
import haw.teamagochi.backend.pet.logic.UcFindPetType;
import haw.teamagochi.backend.pet.service.rest.v1.mapper.PetTypeMapper;
import haw.teamagochi.backend.pet.service.rest.v1.model.PetTypeDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Rest interface for the pet component.
 */
@Path("/v1/pets/types")
@Tag(name = "c) pets/types", description = "Everything about pets.")
@SecurityRequirement(name = "SecurityScheme")
public class PetTypeRestService {

  @Inject
  protected PetTypeMapper petTypeMapper;

  @Inject
  protected UcFindPetType ucFindPetType;

  /**
   * Get all pets.
   *
   * @return a list of all {@link PetTypeDTO PetTypeDTOs}, possibly empty
   */
  @GET
  @Operation(summary = "Get all pet types")
  @APIResponse(responseCode = "200")
  public List<PetTypeDTO> getAllPetTypes() {
    List<PetTypeEntity> entities = ucFindPetType.findAll();
    return petTypeMapper.mapEntityToTransferObject(entities);
  }
}
