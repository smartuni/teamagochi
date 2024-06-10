package haw.teamagochi.backend.device.service.mapper;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.model.DeviceType;
import haw.teamagochi.backend.device.service.rest.v1.mapper.DeviceMapper;
import haw.teamagochi.backend.device.service.rest.v1.model.DeviceDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@QuarkusTest
public class DeviceMapperTest {
  DeviceMapper deviceMapper = DeviceMapper.MAPPER;



  @Test
  public void testMapper() {
    DeviceEntity device = new DeviceEntity("name", DeviceType.FROG);
    DeviceEntity dev2 = new DeviceEntity();
    DeviceDTO deviceDTO = deviceMapper.fromResource(device);
    Assertions.assertEquals(deviceDTO.getDeviceID(), device.getId());
    Assertions.assertEquals(deviceDTO.getDeviceName(), device.getName());
    Assertions.assertEquals(deviceDTO.getDeviceType(), device.getDeviceType().name());
  }




}

