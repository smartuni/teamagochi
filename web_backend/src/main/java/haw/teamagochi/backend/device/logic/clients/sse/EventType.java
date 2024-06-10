package haw.teamagochi.backend.device.logic.clients.sse;

import java.util.Arrays;

/**
 * Server-sent events used by Leshan.
 */
public enum EventType {
  Registration,
  Deregistration,
  Updated,
  Sleeping,
  Awake,
  Coaplog;

  public static EventType fromString(String value) {
    return Arrays.stream(
        values()).filter(elem -> elem.name().equalsIgnoreCase(value)).findAny().orElse(null);
  }
}
