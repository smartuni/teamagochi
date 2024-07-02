package haw.teamagochi.backend.device.dataaccess.model;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Persist-able device representation.
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class DeviceEntity {

  @Id
  @GeneratedValue
  private long id;

  @Nullable
  @Column(unique = true)
  private String identifier;

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

  @Override
  public String toString() {
    return "DeviceEntity{"
        + "id=" + id
        + ", identifier='" + identifier + '\''
        + ", name='" + name + '\''
        + ", deviceType=" + deviceType
        + ", owner=" + owner
        + ", pet=" + pet
        + '}';
  }
}
