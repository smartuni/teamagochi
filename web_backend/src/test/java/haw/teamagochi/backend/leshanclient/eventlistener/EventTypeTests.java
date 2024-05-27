package haw.teamagochi.backend.leshanclient.eventlistener;

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
