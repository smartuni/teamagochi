package haw.teamagochi.backend.device.logic.clients.rest;

import haw.teamagochi.backend.leshanclient.datatypes.rest.*;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.util.Set;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
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
  ObjectResponseDto getClientObject(
      @PathParam("endpoint") String endpoint, @PathParam("object") Integer object);

  @GET
  @Path("/clients/{endpoint}/{object}/{instance}")
  ObjectInstanceResponseDto getClientObjectInstance(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance);

  @GET
  @Path("/clients/{endpoint}/{object}/{instance}/{resource}")
  ResourceResponseDto getClientResource(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance,
      @PathParam("resource") Integer resource);

  /**
   * Write to a Teamagochi Device Resource.
   *
   * <p>Example:
   * <pre>
   *   http//example.com/clients/my-endpoint/3201/0/5550?timeout=300&format=TLV
   *   {"id":1,"kind":"singleResource","value":"my-registration-code","type":"string"}
   * </pre>
   */
  @PUT
  @Path("/clients/{endpoint}/{object}/{instance}/{resource}")
  ResourceResponseDto writeClientResource(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance,
      @PathParam("resource") Integer resource,
      @QueryParam("timeout") Integer timeout,
      @QueryParam("format") String format,
      ResourceDto resourceDto);

  /**
   * Write to a Teamagochi Device Resource.
   *
   * <p>Example:
   * <pre>
   *   http//example.com/clients/my-endpoint/3201/0/5550?timeout=300&format=TLV
   *   {"id":1,"kind":"singleResource","value":"my-registration-code","type":"string"}
   * </pre>
   */
  @PUT
  @Path("/clients/{endpoint}/{object}/{instance}")
  ResourceResponseDto writeClientObjectInstance(
      @PathParam("endpoint") String endpoint,
      @PathParam("object") Integer object,
      @PathParam("instance") Integer instance,
      @QueryParam("timeout") Integer timeout,
      @QueryParam("format") String format,
      ObjectInstanceDto objectInstanceDto);

  @GET
  @Path("/objectspecs/{endpoint}")
  Set<ObjectspecDto> getClientObjectSpecifications(@PathParam("endpoint") String endpoint);
}
