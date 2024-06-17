package haw.teamagochi.backend.device.logic.clients.sse;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EventType}.
 */
public class EventTypeTests {

  @Test
  public void testFromStringIsCaseInsensitive() {
    assertNotNull(EventType.fromString("registration"));
    assertNotNull(EventType.fromString("Registration"));
    assertNotNull(EventType.fromString("REGISTRATION"));
  }
}
