package haw.teamagochi.backend.device.logic.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import haw.teamagochi.backend.device.logic.UcHandleLeshanEvents;
import haw.teamagochi.backend.device.logic.clients.sse.LeshanEventClient;
import haw.teamagochi.backend.leshanclient.datatypes.events.AwakeDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.CoaplogDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.RegistrationDto;
import haw.teamagochi.backend.leshanclient.datatypes.events.UpdatedDto;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Subscribes to Leshan events and passes them to {@link UcHandleLeshanEvents}.
 */
@ApplicationScoped
public class LeshanEventListener {

  private final ObjectMapper objectMapper;

  private final UcHandleLeshanEvents ucHandleLeshanEvents;

  @RestClient
  private LeshanEventClient sseClient;

  LeshanEventListener(UcHandleLeshanEvents ucHandleLeshanEvents) {
    this.ucHandleLeshanEvents = ucHandleLeshanEvents;
    this.objectMapper = new ObjectMapper();
  }

  @Startup
  void receiveRegistrationEvents() {
    sseClient
        .registration().onFailure().recoverWithCompletion()
        .subscribe().with(sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              RegistrationDto dto = objectMapper.readValue(event.data(), RegistrationDto.class);
              ucHandleLeshanEvents.handleRegistration(dto);
            }
        ));
  }

  @Startup
  void receiveDeregistrationEvents() {
    sseClient
        .deregistration().onFailure().recoverWithCompletion()
        .subscribe().with(sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              RegistrationDto dto = objectMapper.readValue(event.data(), RegistrationDto.class);
              ucHandleLeshanEvents.handleDeregistration(dto);
            }
        ));
  }

  @Startup
  void receiveUpdatedEvents() {
    sseClient
        .updated().onFailure().recoverWithCompletion()
        .subscribe().with(sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              UpdatedDto dto = objectMapper.readValue(event.data(), UpdatedDto.class);
              ucHandleLeshanEvents.handleUpdate(dto);
            }
        ));
  }

  @Startup
  void receiveSleepingEvents() {
    sseClient
        .sleeping().onFailure().recoverWithCompletion()
        .subscribe().with(sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              AwakeDto dto = objectMapper.readValue(event.data(), AwakeDto.class);
              ucHandleLeshanEvents.handleSleeping(dto);
            }
        ));
  }

  @Startup
  void receiveAwakeEvents() {
    sseClient
        .awake().onFailure().recoverWithCompletion()
        .subscribe().with(sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              AwakeDto dto = objectMapper.readValue(event.data(), AwakeDto.class);
              ucHandleLeshanEvents.handleAwake(dto);
            }
        ));
  }

  @Startup
  void receiveCoapLogEvents() {
    sseClient
        .coaplog().onFailure().recoverWithCompletion()
        .subscribe().with(sseEvent -> handleEventConsumer(sseEvent, (event) -> {
              CoaplogDto dto = objectMapper.readValue(event.data(), CoaplogDto.class);
              ucHandleLeshanEvents.handleCoapLog(dto);
            }
        ));
  }

  /**
   * Wrapper for {@link CheckedEventConsumer} functions for logging and error handling.
   *
   * @param event which is passed as a param to the wrapped function
   * @param onItem is the wrapped function
   */
  private void handleEventConsumer(
      SseEvent<String> event, CheckedEventConsumer<? super SseEvent<String>> onItem) {
    Log.debug("Handle: " + event.name());
    try {
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
