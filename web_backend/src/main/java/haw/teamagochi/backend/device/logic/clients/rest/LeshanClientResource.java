package haw.teamagochi.backend.device.logic.clients.rest;

import haw.teamagochi.backend.leshanclient.datatypes.rest.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/** Resource for {@link LeshanClientRestclient}. */
@Path("/")
public class LeshanClientResource {

  @RestClient LeshanClientRestclient clientRestclient;

  @GET
  public Set<ClientDto> getClients() {
    return clientRestclient.getClients();
  }

  @GET
  @Path("/clients/{endpoint}")
  public ClientDto getClient(@PathParam("endpoint") String endpoint) {
    return clientRestclient.getClient(endpoint);
  }

  @GET
  @Path("/clients/{endpoint}/{object}")
  public ObjectResponseDto getClientObject(
      @PathParam("endpoint") String endpoint, @PathParam("object") Integer object) {
    return clientRestclient.getClientObject(endpoint, object);
  }

  @GET
  @Path("/clients/{endpoint}/{object}/{instance}")
  public ObjectInstanceResponseDto getClientObjectInstance(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance) {
    return clientRestclient.getClientObjectInstance(endpoint, object, instance);
  }

  @GET
  @Path("/clients/{endpoint}/{object}/{instance}/{resource}")
  public ResourceResponseDto getClientResource(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance,
      @PathParam("resource") Integer resource) {
    return clientRestclient.getClientResource(endpoint, object, instance, resource);
  }

  @GET
  @Path("/objectspecs/{endpoint}")
  public Set<ObjectspecDto> getClientObjectSpecifications(@PathParam("endpoint") String endpoint) {
    return clientRestclient.getClientObjectSpecifications(endpoint);
  }
}
