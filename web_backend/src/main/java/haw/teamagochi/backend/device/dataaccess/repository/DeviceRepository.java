package haw.teamagochi.backend.device.dataaccess.repository;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DeviceRepository implements PanacheRepository<DeviceEntity> {

  public List<DeviceEntity> findByOwner(long userID) {
    return list("owner", userID);
  }
}
