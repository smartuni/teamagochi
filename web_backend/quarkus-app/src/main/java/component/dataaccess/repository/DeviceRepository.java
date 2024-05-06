package component.dataaccess.repository;

import component.dataaccess.model.DeviceEntity;
import component.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeviceRepository implements PanacheRepository<DeviceEntity> {
}
