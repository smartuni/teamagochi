package component.dataaccess.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.sql.Date;
import java.util.Objects;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// TODO: which repo methods need?
// TODO: red im pom.xml? Did I cause that?
// TODO: what happens if constraints violated?
// TODO: better mocks for testing.

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class PetEntity extends PanacheEntity {

  public PetEntity() {}
  
  @NonNull
  @Size(max = 255)
  private String name;

  @NonNull
  @Size(max = 255)
  private String color; // TODO: setter should check if is hex-code

  private int happiness = 0;
  private int wellbeing = 0;
  private int health = 0;
  private int hunger = 0;
  private int cleanliness = 0;
  private int fun = 0;

  @NonNull
  @PositiveOrZero
  private int xp = 0;

  @Nullable
  private Date lastTimeOnDevice;


  @ManyToOne
  @NonNull
  private PetTypeEntity petType;


  @OneToOne
  @Nullable
  private DeviceEntity device;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PetEntity petEntity = (PetEntity) o;
    return id.equals(petEntity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }




}
