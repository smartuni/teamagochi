package haw.teamagochi.backend.leshanclient.eventlistener.client;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Event resource for {@link LeshanEventClient}.
 */
@Path("/event")
@Singleton
public class LeshanEventResource {

  @RestClient
  LeshanEventClient eventClient;

  @GET
  @Path("/")
  public Multi<SseEvent<String>> registration() {
    return eventClient.registration();
  }

  @GET
  @Path("/")
  public Multi<SseEvent<String>> deregistration() {
    return eventClient.deregistration();
  }

  @GET
  @Path("/")
  public Multi<SseEvent<String>> updated() {
    return eventClient.updated();
  }

  @GET
  @Path("/")
  public Multi<SseEvent<String>> sleeping() {
    return eventClient.sleeping();
  }

  @GET
  @Path("/")
  public Multi<SseEvent<String>> awake() {
    return eventClient.awake();
  }

  @GET
  @Path("/")
  public Multi<SseEvent<String>> coaplog() {
    return eventClient.coaplog();
  }

  @GET
  @Path("/")
  public Multi<SseEvent<String>> events() {
    return eventClient.events();
  }
}
