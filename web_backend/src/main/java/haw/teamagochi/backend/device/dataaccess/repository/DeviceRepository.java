package haw.teamagochi.backend.device.dataaccess.repository;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DeviceRepository implements PanacheRepository<DeviceEntity> {

  // TODO: Was passiert wenn user == null?
  public List<DeviceEntity> findByOwner(UserEntity user) {
    return list("owner", user);
  }

  public DeviceEntity findByIdentifier(String identifier) {
    return find("identifier", identifier).firstResult();
  }
}
