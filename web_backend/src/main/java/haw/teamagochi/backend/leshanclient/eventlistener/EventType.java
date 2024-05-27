package haw.teamagochi.backend.leshanclient.eventlistener;

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
