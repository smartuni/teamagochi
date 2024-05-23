package component.dataaccess.repository;

import component.dataaccess.model.PetTypeEntity;
import component.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

}
