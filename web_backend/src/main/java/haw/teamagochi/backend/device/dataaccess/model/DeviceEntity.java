package haw.teamagochi.backend.device.dataaccess.model;
import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
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
public class DeviceEntity {

  public DeviceEntity() {}

  @Id
  @GeneratedValue
  private long id;

  @NonNull
  @Size(max = 255)
  private String name;

  @NonNull
  @Enumerated(EnumType.STRING) // save enum as string
  private DeviceType deviceType;


  @ManyToOne
  @Nullable
  private UserEntity owner;

  @OneToOne
  @Nullable
  private PetEntity pet;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceEntity device = (DeviceEntity) o;
    return id == device.id && name.equals(device.name) && deviceType == device.deviceType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, deviceType);
  }
}
