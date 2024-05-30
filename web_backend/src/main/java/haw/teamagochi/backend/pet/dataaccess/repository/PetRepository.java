package haw.teamagochi.backend.pet.dataaccess.repository;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PetRepository implements PanacheRepository<PetEntity> {

}
