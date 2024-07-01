package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.clients.rest.LeshanClientRestclient;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ObjectInstanceDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ResourceDto;
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

    //createSingleBooleanResource(20, true) // Hungry
    //createSingleBooleanResource(21, true) // Ill
    //createSingleBooleanResource(22, true) // Bored
    //createSingleBooleanResource(23, true) // Dirty

    //createSingleIntegerResource(40, 0); // feed
    //createSingleIntegerResource(41, 0); // medicate
    //createSingleIntegerResource(42, 0); // play
    //createSingleIntegerResource(43, 0); // clean

    ObjectInstanceDto instanceDto = new ObjectInstanceDto();
    instanceDto.kind = "instance";
    instanceDto.id = 0;
    instanceDto.resources = resources;

    ResourceResponseDto response = restClient.writeClientObjectInstance(
        endpoint, PET_OBJECT_ID, instanceDto.id,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT, instanceDto);

    return !response.failure;
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
    resourceDto.value = Integer.toString(value);

    return resourceDto;
  }

  private ResourceDto createSingleBooleanResource(int id, boolean value) {
    ResourceDto resourceDto = new ResourceDto();
    resourceDto.id = id;
    resourceDto.kind = "singleResource";
    resourceDto.type = "boolean";
    resourceDto.value = String.valueOf(value);

    return resourceDto;
  }
}
