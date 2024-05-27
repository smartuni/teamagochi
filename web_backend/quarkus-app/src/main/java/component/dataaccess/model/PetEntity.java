package component.dataaccess.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.sql.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class PetEntity {

  @Id
  @GeneratedValue
  private long id;

  public PetEntity() {}


  @NonNull
  @Size(max = 255)
  private String name;


  /*
  @NonNull
  @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$") // source: https://www.geeksforgeeks.org/how-to-validate-hexadecimal-color-code-using-regular-expression/
  private String color;
  */

  private int happiness;
  private int wellbeing;
  private int health;
  private int hunger;
  private int cleanliness;
  private int fun;

  @PositiveOrZero
  private int xp = 0;

  @Nullable
  @Past
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
    return id == petEntity.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }



}

