package haw.teamagochi.backend.leshanclient.eventlistener.client;

import haw.teamagochi.backend.leshanclient.eventlistener.filter.AwakeEventFilter;
import haw.teamagochi.backend.leshanclient.eventlistener.filter.CoaplogEventFilter;
import haw.teamagochi.backend.leshanclient.eventlistener.filter.DeregistrationEventFilter;
import haw.teamagochi.backend.leshanclient.eventlistener.filter.RegistrationEventFilter;
import haw.teamagochi.backend.leshanclient.eventlistener.filter.SleepingEventFilter;
import haw.teamagochi.backend.leshanclient.eventlistener.filter.UpdatedEventFilter;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.client.SseEvent;
import org.jboss.resteasy.reactive.client.SseEventFilter;

/**
 * Rest client interface for Leshan server-sent events.
 */
@Path("/event")
@RegisterRestClient(configKey = "leshan-event-api")
@Singleton
public interface LeshanEventClient {

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseEventFilter(RegistrationEventFilter.class)
  Multi<SseEvent<String>> registration();

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseEventFilter(DeregistrationEventFilter.class)
  Multi<SseEvent<String>> deregistration();

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseEventFilter(UpdatedEventFilter.class)
  Multi<SseEvent<String>> updated();

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseEventFilter(SleepingEventFilter.class)
  Multi<SseEvent<String>> sleeping();

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseEventFilter(AwakeEventFilter.class)
  Multi<SseEvent<String>> awake();

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseEventFilter(CoaplogEventFilter.class)
  Multi<SseEvent<String>> coaplog();

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  Multi<SseEvent<String>> events();
}
