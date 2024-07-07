package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.clients.rest.LeshanClientRestclient;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ObjectInstanceDto;
import haw.teamagochi.backend.leshanclient.datatypes.common.ResourceDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ResourceResponseDto;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Default implementation for {@link UcPetResourceOperations}.
 */
@ApplicationScoped
public class UcPetResourceOperationsImpl implements UcPetResourceOperations {

  private static final int DEFAULT_COAP_TIMEOUT = 30; // seconds

  private static final String DEFAULT_COAP_FORMAT = "TLV";

  private static final int PET_OBJECT_ID = 32769;

  @RestClient
  private LeshanClientRestclient restClient;

  @Override
  public boolean writePet(String endpoint, PetEntity entity) {
    List<ResourceDto> resources = new ArrayList<>();

    resources.add(createSingleIntegerResource(0, entity.getId()));
    resources.add(createSingleStringResource(1, entity.getName()));
    resources.add(createSingleIntegerResource(2, 11206400));

    resources.add(createSingleIntegerResource(3, entity.getHappiness()));
    resources.add(createSingleIntegerResource(4, entity.getWellbeing()));
    resources.add(createSingleIntegerResource(5, entity.getHealth()));
    resources.add(createSingleIntegerResource(6, entity.getXp()));
    resources.add(createSingleIntegerResource(7, entity.getHunger()));
    resources.add(createSingleIntegerResource(8, entity.getCleanliness()));
    resources.add(createSingleIntegerResource(9, entity.getFun()));

    resources.add(createSingleBooleanResource(20, false)); // Hungry
    resources.add(createSingleBooleanResource(21, false)); // Ill
    resources.add(createSingleBooleanResource(22, false)); // Bored
    resources.add(createSingleBooleanResource(23, false)); // Dirty

    resources.add(createSingleIntegerResource(40, 0)); // feed
    resources.add(createSingleIntegerResource(41, 0)); // medicate
    resources.add(createSingleIntegerResource(42, 0)); // play
    resources.add(createSingleIntegerResource(43, 0)); // clean

    ObjectInstanceDto instanceDto = new ObjectInstanceDto();
    instanceDto.kind = "instance";
    instanceDto.id = 0;
    instanceDto.resources = resources;

    ResourceResponseDto response = restClient.writeClientObjectInstance(
        endpoint, PET_OBJECT_ID, instanceDto.id,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT, true, instanceDto);

    return !response.failure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void observePetInteractions(String endpoint) {
    restClient.observeClientResource(endpoint, PET_OBJECT_ID, 0, 40,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT);
    restClient.observeClientResource(endpoint, PET_OBJECT_ID, 0, 41,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT);
    restClient.observeClientResource(endpoint, PET_OBJECT_ID, 0, 42,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT);
    restClient.observeClientResource(endpoint, PET_OBJECT_ID, 0, 43,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT);
  }

  private ResourceDto createSingleStringResource(int id, String value) {
    ResourceDto resourceDto = new ResourceDto();
    resourceDto.id = id;
    resourceDto.kind = "singleResource";
    resourceDto.type = "string";
    resourceDto.value = value;

    return resourceDto;
  }

  private ResourceDto createSingleIntegerResource(int id, Long value) {
    return createSingleIntegerResource(id, value.intValue());
  }

  private ResourceDto createSingleIntegerResource(int id, Integer value) {
    ResourceDto resourceDto = new ResourceDto();
    resourceDto.id = id;
    resourceDto.kind = "singleResource";
    resourceDto.type = "integer";
    resourceDto.value = value;

    return resourceDto;
  }

  private ResourceDto createSingleBooleanResource(int id, boolean value) {
    ResourceDto resourceDto = new ResourceDto();
    resourceDto.id = id;
    resourceDto.kind = "singleResource";
    resourceDto.type = "boolean";
    resourceDto.value = value;

    return resourceDto;
  }
}
