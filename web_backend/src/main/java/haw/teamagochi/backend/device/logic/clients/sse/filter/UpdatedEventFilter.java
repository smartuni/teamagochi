package haw.teamagochi.backend.device.logic.clients.sse.filter;

import haw.teamagochi.backend.device.logic.clients.sse.EventType;
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