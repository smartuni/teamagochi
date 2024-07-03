package haw.teamagochi.backend.device.logic.clients.rest;

import static org.junit.jupiter.api.Assertions.*;

import haw.teamagochi.backend.leshanclient.datatypes.rest.ClientDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ObjectInstanceResponseDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ObjectResponseDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ObjectspecDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ResourceDto;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ResourceResponseDto;
import io.quarkus.test.junit.QuarkusTest;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LeshanClientResource}.
 *
 * <p>Does not run by default and needs to triggered manually as the Leshan demo server needs to be
 * available and the endpoint must exist. See @Disabled annotation.
 */
@Disabled
@QuarkusTest
public class LeshanClientResourceTests {

  private static final String endpoint = "EthernetIO_8DABB138";

  @RestClient private LeshanClientRestclient clientService;

  /** Endpoint: /api/objectspecs/{endpoint}. */

  @Test
  public void testObjectspecs() {
    // When
    Set<ObjectspecDto> dtoSet = clientService.getClientObjectSpecifications(endpoint);

    // Then
    assertNotNull(dtoSet);
    assertFalse(dtoSet.isEmpty());

    // Print
    // System.out.println(dtoSet);
  }

  /** Endpoint: /api/clients. */
  @Test
  public void testGetAllClients() {
    // When
    Set<ClientDto> dtoSet = clientService.getClients();

    // Then
    assertNotNull(dtoSet);
    assertFalse(dtoSet.isEmpty());

    // Print
    // System.out.println(dtoSet);
  }

  /** Endpoint: /api/clients/{endpoint}. */
  @Test
  public void testGetClient() {
    // When
    ClientDto dto = clientService.getClient(endpoint);

    // Then
    assertEquals(endpoint, dto.endpoint);
    assertNotNull(dto.registrationId);
    assertNotNull(dto.registrationDate);
    assertNotNull(dto.lastUpdate);
    assertNotNull(dto.address);
    assertNotNull(dto.lwM2mVersion);
    assertNotNull(dto.lifetime);
    assertNotNull(dto.bindingMode);
    assertNotNull(dto.rootPath);
    assertNotNull(dto.objectLinks);
    assertNotNull(dto.secure);
    assertNotNull(dto.additionalRegistrationAttributes);
    assertNotNull(dto.queuemode);
    assertNotNull(dto.availableInstances);

    // TODO assertions
    // List<ObjectLinkDto> objectLinksDtoList = dto.objectLinks;
    // AvailableInstancesDto availableInstancesDto = dto.availableInstances;

    // Print
    System.out.println(dto);
    // System.out.println(objectLinksDtoList);
    // System.out.println(availableInstancesDto);
  }

  /** Endpoint: /api/clients/{endpoint}/{object}. */

  @Test
  public void testGetClientObject() {
    // Given
    int objectId = 1;

    // When
    ObjectResponseDto dto = clientService.getClientObject(endpoint, objectId);

    // Then
    assertEquals("CONTENT(205)", dto.status);
    assertTrue(dto.valid);
    assertTrue(dto.success);
    assertFalse(dto.failure);
    assertEquals("obj", dto.content.kind);
    assertNotNull(dto.content.instances);
    assertEquals(objectId, dto.content.id);

    // Print
    // List<ObjectInstanceDto> instances = dto.content.instances;
    // System.out.println(instances);
  }

  /** Endpoint: /api/clients/{endpoint}/{object}/{instance}. */

  @Test
  public void testGetClientObjectInstance() {
    // Given
    int objectId = 1;
    int instanceId = 0;

    // When
    ObjectInstanceResponseDto dto =
        clientService.getClientObjectInstance(endpoint, objectId, instanceId);

    // Then
    assertEquals("CONTENT(205)", dto.status);
    assertTrue(dto.valid);
    assertTrue(dto.success);
    assertFalse(dto.failure);
    assertNotNull(dto.content);

    assertEquals("instance", dto.content.kind);
    assertNotNull(dto.content.resources);
    assertEquals(instanceId, dto.content.id);

    // Print
    // List<ResourceDto> resources = dto.content.resources;
    // System.out.println(resources);
  }

  /** Endpoint: /api/clients/{endpoint}/{object}/{instance}. */

  @Test
  public void testGetClientObjectInstance_NotFound() {
    // Given
    int objectId = 1;
    int instanceId = 1; // Is never defined

    // When
    ObjectInstanceResponseDto dto =
        clientService.getClientObjectInstance(endpoint, objectId, instanceId);

    // Then
    assertEquals("NOT_FOUND(404)", dto.status);
    assertTrue(dto.valid);
    assertFalse(dto.success);
    assertTrue(dto.failure);
    assertNull(dto.content);

    // Print
    // System.out.println(dto);
  }

  /** Endpoint: /api/clients/{endpoint}/{object}/{instance}/{resource}. */

  @Test
  public void testGetClientResource_singleResource() {
    // Given
    int objectId = 1;
    int instanceId = 0;
    int resourceId = 0;

    // When
    ResourceResponseDto dto =
        clientService.getClientResource(endpoint, objectId, instanceId, resourceId);

    // Then
    assertEquals("CONTENT(205)", dto.status);
    assertTrue(dto.valid);
    assertTrue(dto.success);
    assertFalse(dto.failure);
    assertNotNull(dto.content);

    ResourceDto contentDto = dto.content;
    assertEquals("singleResource", contentDto.kind);
    assertEquals(resourceId, contentDto.id);
    assertEquals("INTEGER", contentDto.type);
    assertNotNull(contentDto.value);
    assertNull(contentDto.values); // because type == singleResource

    // Print
    // System.out.println(contentDto);
  }

  /** Endpoint: /api/clients/{endpoint}/{object}/{instance}/{resource}. */

  @Test
  public void testGetClientResource_multiResource() {
    // Given
    int objectId = 3;
    int instanceId = 0;
    int resourceId = 11;

    // When
    ResourceResponseDto dto =
        clientService.getClientResource(endpoint, objectId, instanceId, resourceId);

    // Then
    assertEquals("CONTENT(205)", dto.status);
    assertTrue(dto.valid);
    assertTrue(dto.success);
    assertFalse(dto.failure);
    assertNotNull(dto.content);

    ResourceDto contentDto = dto.content;
    assertEquals("multiResource", contentDto.kind);
    assertEquals(resourceId, contentDto.id);
    assertEquals("INTEGER", contentDto.type);
    assertNull(contentDto.value); // because type == multiResource
    // TODO assertions
    // assertNotNull(Integer.valueOf(contentDto.values));
    // System.out.println(contentDto.values);
    // System.out.println(contentDto.values.getAdditionalProperties().get("0"));

    // Print
    // System.out.println(contentDto);
  }

  /** Endpoint: /api/clients/{endpoint}/{object}/{instance}/{resource}. */

  @Test
  public void testGetClientResource_NotFound() {
    // Given
    int objectId = 1;
    int instanceId = 0;
    int resourceId = 20; // Is never defined

    // When
    ResourceResponseDto dto =
        clientService.getClientResource(endpoint, objectId, instanceId, resourceId);

    // Then
    assertEquals("NOT_FOUND(404)", dto.status);
    assertTrue(dto.valid);
    assertFalse(dto.success);
    assertTrue(dto.failure);
    assertNull(dto.content);

    // Print
    // System.out.println(dto);
  }
}
