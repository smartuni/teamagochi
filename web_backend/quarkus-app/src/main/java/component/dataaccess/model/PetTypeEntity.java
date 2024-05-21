package component.dataaccess.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class PetTypeEntity extends PanacheEntity {

  public PetTypeEntity() {}

  @NonNull
  @Size(max = 255)
  private String name;


}
