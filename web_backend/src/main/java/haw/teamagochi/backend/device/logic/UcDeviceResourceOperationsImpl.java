package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.clients.rest.DeviceStatus;
import haw.teamagochi.backend.device.logic.clients.rest.LeshanClientRestclient;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ResourceDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ResourceResponseDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Default implementation for {@link UcDeviceResourceOperations}.
 */
@ApplicationScoped
public class UcDeviceResourceOperationsImpl implements UcDeviceResourceOperations {

  private static final int DEFAULT_COAP_TIMEOUT = 30; // seconds

  private static final String DEFAULT_COAP_FORMAT = "TLV";

  private static final int DEVICE_OBJECT_ID = 32770;

  @RestClient
  private LeshanClientRestclient restClient;

  /**
   * {@inheritDoc}
   */
  public boolean writeStatus(String endpoint, DeviceStatus status) {
    ResourceDto resourceDto = createStatusResourceDto(status);

    ResourceResponseDto response = restClient.writeClientResource(
        endpoint, DEVICE_OBJECT_ID, 0, resourceDto.id,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT, resourceDto
    );

    return !response.failure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean writeRegistrationCode(String endpoint, String registrationCode) {
    ResourceDto resourceDto = createRegistrationCodeResourceDto(registrationCode);

    ResourceResponseDto response = restClient.writeClientResource(
        endpoint, DEVICE_OBJECT_ID, 0, resourceDto.id,
        DEFAULT_COAP_TIMEOUT, DEFAULT_COAP_FORMAT, resourceDto);

    return !response.failure;
  }

  private ResourceDto createStatusResourceDto(DeviceStatus status) {
    return createSingleStringResource(0, status.name().toUpperCase());
  }

  private ResourceDto createRegistrationCodeResourceDto(String registrationCode) {
    return createSingleStringResource(1, registrationCode);
  }

  private ResourceDto createSingleStringResource(int id, String value) {
    ResourceDto resourceDto = new ResourceDto();
    resourceDto.id = id;
    resourceDto.kind = "singleResource";
    resourceDto.type = "string";
    resourceDto.value = value;

    return resourceDto;
  }
}
