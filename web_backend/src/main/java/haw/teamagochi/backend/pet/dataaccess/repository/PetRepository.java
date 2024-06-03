package haw.teamagochi.backend.pet.dataaccess.repository;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PetRepository implements PanacheRepository<PetEntity> {

  public List<PetEntity> findByOwner(long userID) {
    return list("owner", userID); //TODO: list oder find?

  }

  /**
   * Returns null if there are no results
   */
  public PetEntity findByName(String name) {
    return find("name", name).firstResult();
  }

}
