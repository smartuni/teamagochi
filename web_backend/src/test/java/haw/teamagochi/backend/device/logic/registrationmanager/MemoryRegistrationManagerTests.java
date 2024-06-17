package haw.teamagochi.backend.device.logic.registrationmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MemoryRegistrationManager}.
 */
public class MemoryRegistrationManagerTests {

  private MemoryRegistrationManager registrationManager;

  @BeforeEach
  void init() {
    registrationManager = new MemoryRegistrationManager();
  }

  @AfterEach
  void teardown() {
    registrationManager = null;
  }

  @Test
  void testAddClient() {
    registrationManager.addClient("my-endpoint");
    registrationManager.addClient("my-endpoint");
    registrationManager.addClient("my-endpoint-2");

    assertEquals(2, registrationManager.size());
  }

  @Test
  void testRemoveClient() {
    registrationManager.addClient("my-endpoint");
    registrationManager.addClient("my-endpoint-2");
    registrationManager.removeClient("my-endpoint");

    assertEquals(1, registrationManager.size());
  }

  @Test
  void testRemoveOutdatedClients() {
    registrationManager.setRegistrationLifetime(1);
    registrationManager.addClient("my-endpoint");
    registrationManager.addClient("my-endpoint-2");

    // Sleep 2 seconds
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    registrationManager.addClient("my-endpoint-3");
    int initialSize = registrationManager.size();
    registrationManager.removeOutdatedClients();

    assertEquals(3, initialSize);
    assertEquals(1, registrationManager.size());
  }
}
