package haw.teamagochi.backend.leshanclient.eventlistener.filter;

import haw.teamagochi.backend.leshanclient.eventlistener.EventType;
import org.jboss.resteasy.reactive.client.SseEvent;

/**
 * Filter Awake events.
 */
public class AwakeEventFilter extends AbstractEventFilter<String> {
  @Override
  public boolean test(SseEvent<String> event) {
    return matchesName(event, EventType.Awake);
  }
}