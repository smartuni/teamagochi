package haw.teamagochi.backend.device.logic.clients.sse.filter;

import haw.teamagochi.backend.device.logic.clients.sse.EventType;
import java.util.function.Predicate;
import org.jboss.resteasy.reactive.client.SseEvent;

abstract class AbstractEventFilter<T> implements Predicate<SseEvent<T>>  {

  /**
   * Template method which needs to be overridden by the extending class.
   *
   * @param event the input argument
   *
   * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
   */
  public abstract boolean test(SseEvent<T> event);

  /**
   * Test if an event is of a given event type.
   *
   * @param event to be tested
   * @param eventType to test against
   *
   * @return {@code true} if the event type matches, otherwise {@code false}
   */
  protected boolean matchesName(SseEvent<T> event, EventType eventType) {
    return hasName(event) && eventType.equals(EventType.fromString(event.name()));
  }

  /**
   * Test if an event type is set.
   *
   * @param event to be tested
   *
   * @return {@code true} if the event has a name, otherwise {@code false}
   */
  protected boolean hasName(SseEvent<T> event) {
    return event.name() != null;
  }
}
