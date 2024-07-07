package haw.teamagochi.backend.device.logic.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import haw.teamagochi.backend.device.logic.UcHandleLeshanEvents;
import haw.teamagochi.backend.device.logic.clients.sse.LeshanEventClient;
import haw.teamagochi.backend.leshanclient.datatypes.events.AwakeDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.CoaplogDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.NotificationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.RegistrationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.UpdatedDto;
import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Subscribes to Leshan events and passes them to {@link UcHandleLeshanEvents}.
 */
@UnlessBuildProfile("test")
@ApplicationScoped
public class LeshanEventListener {

  private static final Logger LOGGER = Logger.getLogger(LeshanEventListener.class);

  private final ObjectMapper objectMapper;

  private final UcHandleLeshanEvents ucHandleLeshanEvents;

  @RestClient
  private LeshanEventClient sseClient;

  @ConfigProperty(name = "leshan.client-endpoint-name.prefix")
  protected String clientEndpointNamePrefix;

  @ConfigProperty(name = "leshan.client-endpoint-name.filter-mode")
  protected String clientEndpointNameFilterMode;

  LeshanEventListener(UcHandleLeshanEvents ucHandleLeshanEvents) {
    this.ucHandleLeshanEvents = ucHandleLeshanEvents;
    this.objectMapper = new ObjectMapper();
  }

  @Startup
  void receiveRegistrationEvents() {
    sseClient
        .registration()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              RegistrationDto dto = objectMapper.readValue(event.data(), RegistrationDto.class);
              if (isAcceptableClientEndpointName(dto.endpoint)) {
                ucHandleLeshanEvents.handleRegistration(dto);
              }
            }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  @Startup
  void receiveDeregistrationEvents() {
    sseClient
        .deregistration()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              RegistrationDto dto = objectMapper.readValue(event.data(), RegistrationDto.class);
              if (isAcceptableClientEndpointName(dto.endpoint)) {
                ucHandleLeshanEvents.handleDeregistration(dto);
              }
            }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  @Startup
  void receiveUpdatedEvents() {
    sseClient
        .updated()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              UpdatedDto dto = objectMapper.readValue(event.data(), UpdatedDto.class);
              if (isAcceptableClientEndpointName(dto.registration.endpoint)) {
                ucHandleLeshanEvents.handleUpdate(dto);
              }
            }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  @Startup
  void receiveNotificationEvents() {
    sseClient
        .notification()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
                  NotificationDto dto = objectMapper.readValue(event.data(), NotificationDto.class);
                  if (isAcceptableClientEndpointName(dto.ep)) {
                    ucHandleLeshanEvents.handleNotification(dto);
                  }
                }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  @Startup
  void receiveSleepingEvents() {
    sseClient
        .sleeping()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              AwakeDto dto = objectMapper.readValue(event.data(), AwakeDto.class);
              if (isAcceptableClientEndpointName(dto.ep)) {
                ucHandleLeshanEvents.handleSleeping(dto);
              }
            }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  @Startup
  void receiveAwakeEvents() {
    sseClient
        .awake()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              AwakeDto dto = objectMapper.readValue(event.data(), AwakeDto.class);
              if (isAcceptableClientEndpointName(dto.ep)) {
                ucHandleLeshanEvents.handleAwake(dto);
              }
            }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  @Startup
  void receiveCoapLogEvents() {
    sseClient
        .coaplog()
        .emitOn(Infrastructure.getDefaultWorkerPool())
        .onFailure().invoke(failure -> LOGGER.error(failure.getMessage()))
        .subscribe().with(
            sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              CoaplogDto dto = objectMapper.readValue(event.data(), CoaplogDto.class);
              if (isAcceptableClientEndpointName(dto.ep)) {
                ucHandleLeshanEvents.handleCoapLog(dto);
              }
            }
        ), failure -> LOGGER.error(failure.getMessage()));
  }

  /**
   * Check if the event is about a Teamagochi device.
   *
   * <p>Note that Section "7.4.1. Endpoint Client Name" of the LwM2M spec says:
   *   "[...] the Endpoint Client Name is unauthenticated and can be set to arbitrary
   *   value by a misconfigured or malicious client and hence MUST NOT be used alone for
   *   any decision making without prior matching the Endpoint Client Name against the
   *   identifier used with the security protocol protecting LwM2M communication".
   *
   * @param value to evaluate
   */
  private boolean isAcceptableClientEndpointName(String value) {
    if (clientEndpointNameFilterMode.equals("write")) {
      return true;
    }
    return clientEndpointNamePrefix != null && value.startsWith(clientEndpointNamePrefix);
  }

  /**
   * Wrapper for {@link CheckedEventConsumer} functions for logging and error handling.
   *
   * @param event which is passed as a param to the wrapped function
   * @param onItem is the wrapped function
   */
  private void handleEventConsumer(
      SseEvent<String> event, CheckedEventConsumer<? super SseEvent<String>> onItem) {
    try {
      Log.debug("Handle: " + event.name());
      onItem.accept(event);
    } catch (JsonProcessingException exception) {
      Log.error("Error processing JSON: " + event.name(), exception);
    }
  }

  /**
   * A checked {@link java.util.function.Consumer} which throws {@link JsonProcessingException}.
   *
   * @param <T> event type
   */
  @FunctionalInterface
  private interface CheckedEventConsumer<T extends SseEvent<String>> {
    void accept(T event) throws JsonProcessingException;
  }
}
