package haw.teamagochi.backend.pet.dataaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Persist-able pet type representation.
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class PetTypeEntity {

  @Id
  @GeneratedValue
  private long id;

  @NonNull
  @Size(max = 255)
  private String name;

  @Override
  public String toString() {
    return "PetTypeEntity{"
        + "id=" + id
        + ", name='" + name + '\''
        + '}';
  }
}
