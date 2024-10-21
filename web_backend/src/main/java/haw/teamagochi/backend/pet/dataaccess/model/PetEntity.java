package haw.teamagochi.backend.pet.dataaccess.model;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Persist-able pet representation.
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class PetEntity {

  @Id
  @GeneratedValue
  private long id;

  @NonNull
  @ManyToOne
  private UserEntity owner;

  @NonNull
  @Size(max = 255)
  private String name;

  @Nullable
  @Past
  private Date lastTimeOnDevice;

  @ManyToOne
  @NonNull
  @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
  private PetTypeEntity petType;

  @PositiveOrZero private int happiness = 0;
  @PositiveOrZero private int wellbeing = 0;
  @PositiveOrZero private int health = 100;
  @PositiveOrZero private int hunger = 0;
  @PositiveOrZero private int cleanliness = 100;
  @PositiveOrZero private int fun = 0;
  @PositiveOrZero private int xp = 0;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PetEntity petEntity = (PetEntity) o;
    return id == petEntity.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "PetEntity{"
        + "id=" + id
        + ", owner=" + owner
        + ", name='" + name + '\''
        + ", happiness=" + happiness
        + ", wellbeing=" + wellbeing
        + ", health=" + health
        + ", hunger=" + hunger
        + ", cleanliness=" + cleanliness
        + ", fun=" + fun
        + ", xp=" + xp
        + ", lastTimeOnDevice=" + lastTimeOnDevice
        + ", petType=" + petType
        + '}';
  }
}

