package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
