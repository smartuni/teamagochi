package component.logic;

import component.dataaccess.model.DeviceEntity;
import component.dataaccess.repository.DeviceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class DeviceServiceImpl {
  @Inject
  DeviceRepository deviceRepository;

  public DeviceEntity createDevice() {
    DeviceEntity device = new DeviceEntity();
    deviceRepository.persist(device);
    return device;
  }

}
