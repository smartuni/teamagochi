package component.dataaccess.repository;


import component.dataaccess.model.PetEntity;
import component.dataaccess.model.PetTypeEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PetTypeRepository implements PanacheRepository<PetTypeEntity> {

}
