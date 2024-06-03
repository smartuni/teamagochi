package haw.teamagochi.backend.device.logic.clients.rest;

import haw.teamagochi.backend.leshanclient.datatypes.rest.*;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Rest client for the Leshan Server clients interface.
 */
@Path("/")
@RegisterRestClient(configKey = "leshan-client-api")
@Singleton
public interface LeshanClientRestclient {

  @GET
  @Path("/clients")
  Set<ClientDto> getClients();

  @GET
  @Path("/clients/{endpoint}")
  ClientDto getClient(@PathParam("endpoint") String endpoint);

  @GET
  @Path("/clients/{endpoint}/{object}")
  ObjectResponseDto getClientObject(@PathParam("endpoint") String endpoint, @PathParam("object") Integer object);

  @GET
  @Path("/clients/{endpoint}/{object}/{instance}")
  ObjectInstanceResponseDto getClientObjectInstance(@PathParam("endpoint") String endpoint, @PathParam("object") Integer object, @PathParam("instance") Integer instance);

  @GET
  @Path("/clients/{endpoint}/{object}/{instance}/{resource}")
  ResourceResponseDto getClientResource(@PathParam("endpoint") String endpoint, @PathParam("object") Integer object, @PathParam("instance") Integer instance, @PathParam("resource") Integer resource);

  @GET
  @Path("/objectspecs/{endpoint}")
  Set<ObjectspecDto> getClientObjectSpecifications(@PathParam("endpoint") String endpoint);

//  @GET
//  @Path("/{id}")
//  ClientDto getById(@PathParam("id") String id);

//  @GET
//  @Path("/{id}/{objectId}/{objectInstanceId}/{resourceId}")
//  ClientDto getById(@PathParam("id") String id, @PathParam("objectId") Integer objectId, @PathParam("objectInstanceId") Integer objectInstanceId,  @PathParam("resourceId") Integer resourceId);
}
