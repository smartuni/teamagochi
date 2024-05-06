package component.dataaccess.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class UserEntity extends PanacheEntity  {


  public UserEntity() {}

  @NonNull
  @Column(unique = true)
  private UUID externalID; //TODO: which datatype is in db?

  @OneToMany //TODO: list or nah?
  private List<PetEntity> pets = new ArrayList<>();

  @OneToMany
  private List<DeviceEntity> devices = new ArrayList<>();

}
