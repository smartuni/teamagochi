package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.device.dataaccess.repository.DeviceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {

  @Inject
  DeviceRepository deviceRepository;

  @Override
  @Transactional
  public DeviceEntity createDevice() {
    DeviceEntity device = new DeviceEntity();
    deviceRepository.persist(device);
    return device;
  }

  @Override
  @Transactional
  public boolean deviceExists(long id) {
    DeviceEntity device = deviceRepository.findById(id);
    return device != null;
  }

}
