package haw.teamagochi.backend.device.logic.clients.sse.filter;

import haw.teamagochi.backend.device.logic.clients.sse.EventType;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Filter Awake events.
 */
public class NotificationEventFilter extends AbstractEventFilter<String> {
  @Override
  public boolean test(SseEvent<String> event) {
    return matchesName(event, EventType.Notification);
  }
}