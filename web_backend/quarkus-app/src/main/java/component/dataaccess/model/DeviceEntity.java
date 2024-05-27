package component.dataaccess.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Embedded;
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
public class DeviceEntity{

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

}
