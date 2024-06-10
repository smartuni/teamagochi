package haw.teamagochi.backend.device.logic.clients.sse;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Event resource for {@link LeshanEventClient}.
 */
@Singleton
public class LeshanEventResource {

  @RestClient
  LeshanEventClient eventClient;

  @GET
  public Multi<SseEvent<String>> registration() {
    return eventClient.registration();
  }

  @GET
  public Multi<SseEvent<String>> deregistration() {
    return eventClient.deregistration();
  }

  @GET
  public Multi<SseEvent<String>> updated() {
    return eventClient.updated();
  }

  @GET
  public Multi<SseEvent<String>> sleeping() {
    return eventClient.sleeping();
  }

  @GET
  public Multi<SseEvent<String>> awake() {
    return eventClient.awake();
  }

  @GET
  public Multi<SseEvent<String>> coaplog() {
    return eventClient.coaplog();
  }

  @GET
  public Multi<SseEvent<String>> events() {
    return eventClient.events();
  }
}
