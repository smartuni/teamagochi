package haw.teamagochi.backend.pet.dataaccess.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class PetTypeEntity {

  @Id
  @GeneratedValue
  private long id;

  public PetTypeEntity() {}

  @NonNull
  @Size(max = 255)
  private String name;


}
