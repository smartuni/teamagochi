package haw.teamagochi.backend.user.dataaccess.repository;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {
  public UserEntity findByExternalId(UUID uuid) {
    return find("externalID", uuid).firstResult();
  }
}
