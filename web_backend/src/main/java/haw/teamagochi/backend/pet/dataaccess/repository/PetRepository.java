package haw.teamagochi.backend.pet.dataaccess.repository;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PetRepository implements PanacheRepository<PetEntity> {

  public List<PetEntity> findByOwner(UserEntity user) {
    return list("owner", user);

  }

  /**
   * Returns null if there are no results
   */
  public PetEntity findByName(String name) {
    return find("name", name).firstResult();
  }

}
