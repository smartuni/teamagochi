package haw.teamagochi.backend.device.logic.clients.rest;

import haw.teamagochi.backend.leshanclient.datatypes.rest.*;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Resource for {@link LeshanClientRestclient}.
 */
public class LeshanClientResource {

  @RestClient LeshanClientRestclient clientRestclient;

  public Set<ClientDto> getClients() {
    return clientRestclient.getClients();
  }

  public ClientDto getClient(@PathParam("endpoint") String endpoint) {
    return clientRestclient.getClient(endpoint);
  }

  public ObjectResponseDto getClientObject(
      @PathParam("endpoint") String endpoint, @PathParam("object") Integer object) {
    return clientRestclient.getClientObject(endpoint, object);
  }

  public ObjectInstanceResponseDto getClientObjectInstance(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance) {
    return clientRestclient.getClientObjectInstance(endpoint, object, instance);
  }

  public ResourceResponseDto observeClientResource(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance,
      @PathParam("resource") Integer resource,
      @QueryParam("timeout") Integer timeout,
      @QueryParam("format") String format) {
    return clientRestclient.observeClientResource(
        endpoint, object, instance, resource, timeout, format);
  }

  public ResourceResponseDto getClientResource(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance,
      @PathParam("resource") Integer resource) {
    return clientRestclient.getClientResource(endpoint, object, instance, resource);
  }

  public Set<ObjectspecDto> getClientObjectSpecifications(@PathParam("endpoint") String endpoint) {
    return clientRestclient.getClientObjectSpecifications(endpoint);
  }
}
