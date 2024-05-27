package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {

  @Inject
  DeviceRepository deviceRepository;

  @Override
  public DeviceEntity createDevice() {
    DeviceEntity device = new DeviceEntity();
    deviceRepository.persist(device);
    return device;
  }

  @Override
  public boolean deviceExists(long id) {
    DeviceEntity device = deviceRepository.findById(id);
    return device != null;
  }

}
