package haw.teamagochi.backend.leshanclient.eventlistener.filter;

import haw.teamagochi.backend.leshanclient.eventlistener.EventType;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Filter Deregistration events.
 */
public class DeregistrationEventFilter extends AbstractEventFilter<String> {
  @Override
  public boolean test(SseEvent<String> event) {
    return matchesName(event, EventType.Deregistration);
  }
}