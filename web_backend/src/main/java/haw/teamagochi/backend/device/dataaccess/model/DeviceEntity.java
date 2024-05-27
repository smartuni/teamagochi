package haw.teamagochi.backend.device.dataaccess.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
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

  // oh nooo, I changed smt. Plz recompile

  public DeviceEntity() {}

  @Id
  @GeneratedValue
  private long id;

  @NonNull
  @Size(max = 255)
  private String name;

  @Embedded
  @NonNull
  private DeviceType deviceType;


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
