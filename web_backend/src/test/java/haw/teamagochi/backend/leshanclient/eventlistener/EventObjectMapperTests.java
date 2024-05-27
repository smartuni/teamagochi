package haw.teamagochi.backend.leshanclient.eventlistener;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import haw.teamagochi.backend.leshanclient.sse.AwakeDto;
import haw.teamagochi.backend.leshanclient.sse.CoaplogDto;
import haw.teamagochi.backend.leshanclient.sse.RegistrationDto;
import haw.teamagochi.backend.leshanclient.sse.UpdatedDto;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test mapping of JSON to data transfer objects.
 */
public class EventObjectMapperTests {

  private ObjectMapper objectMapper;

  @BeforeEach
  void init() {
    objectMapper = new ObjectMapper();
  }

  @AfterEach
  void teardown() {
    objectMapper = null;
  }

  /**
   * Registration and Deregistration share the same transfer object.
   */
  @Test
  public void testRegistrationMapper() throws IOException {
    File jsonFile = new File("src/test/resources/fixtures/json/Registration.json");
    RegistrationDto dto = objectMapper.readValue(jsonFile, RegistrationDto.class);

    assertEquals("Hq5sYb6YE3", dto.registrationId);
  }

  @Test
  public void testUpdatedMapper() throws IOException {
    File jsonFile = new File("src/test/resources/fixtures/json/Updated.json");
    UpdatedDto dto = objectMapper.readValue(jsonFile, UpdatedDto.class);

    assertEquals("Tuc2Qoa9Fw", dto.update.registrationId);
  }

  /**
   * Awake and Sleeping share the same transfer object.
   */
  @Test
  public void testAwakeMapper() throws IOException {
    File jsonFile = new File("src/test/resources/fixtures/json/Awake.json");
    AwakeDto dto = objectMapper.readValue(jsonFile, AwakeDto.class);

    assertEquals("urn:imei:869410050031922", dto.ep);
  }

  @Test
  public void testCoaplogMapper() throws IOException {
    File jsonFile = new File("src/test/resources/fixtures/json/Coaplog.json");
    CoaplogDto dto = objectMapper.readValue(jsonFile, CoaplogDto.class);

    assertEquals(1715613660326L, dto.timestamp);
  }
}
