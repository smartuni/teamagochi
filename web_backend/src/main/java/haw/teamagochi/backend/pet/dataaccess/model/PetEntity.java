package haw.teamagochi.backend.pet.dataaccess.model;

import haw.teamagochi.backend.device.dataaccess.model.DeviceEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
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
  @ManyToOne
  private UserEntity owner;


  @NonNull
  @Size(max = 255)
  private String name;


  /*
  @NonNull
  @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$") // source: https://www.geeksforgeeks.org/how-to-validate-hexadecimal-color-code-using-regular-expression/
  private String color;
  */

  private int happiness = 0;
  private int wellbeing = 0;
  private int health = 0;
  private int hunger = 0;
  private int cleanliness = 0;
  private int fun = 0;

  @PositiveOrZero
  private int xp = 0;

  @Nullable
  @Past
  private Date lastTimeOnDevice;


  @ManyToOne
  @NonNull
  private PetTypeEntity petType;



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

