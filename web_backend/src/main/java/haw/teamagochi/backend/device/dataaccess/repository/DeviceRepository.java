package haw.teamagochi.backend.device.dataaccess.repository;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceRepository implements PanacheRepository<DeviceEntity> {
}
