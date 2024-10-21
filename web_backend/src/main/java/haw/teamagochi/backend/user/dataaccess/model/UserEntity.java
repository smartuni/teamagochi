package haw.teamagochi.backend.user.dataaccess.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Persist-able user representation.
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity  {

  @Id
  @GeneratedValue
  private long id;

  @NonNull
  @Column(unique = true)
  private UUID externalID;

  @Override
  public String toString() {
    return "UserEntity{"
        + "id=" + id
        + ", externalID=" + externalID
        + '}';
  }
}
