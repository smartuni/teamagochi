package haw.teamagochi.backend.leshanclient.eventlistener.filter;

import haw.teamagochi.backend.leshanclient.eventlistener.EventType;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Filter Updated events.
 */
public class UpdatedEventFilter extends AbstractEventFilter<String> {
  @Override
  public boolean test(SseEvent<String> event) {
    return matchesName(event, EventType.Updated);
  }
}