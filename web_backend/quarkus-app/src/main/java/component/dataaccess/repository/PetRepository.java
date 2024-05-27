package component.dataaccess.repository;

import component.dataaccess.model.PetEntity;
import component.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PetRepository implements PanacheRepository<PetEntity> {

}
