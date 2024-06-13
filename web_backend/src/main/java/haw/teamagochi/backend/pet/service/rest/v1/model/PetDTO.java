package haw.teamagochi.backend.pet.service.rest.v1.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A data transfer object (DTO) for pets.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RegisterForReflection
public class PetDTO {
  Long id;
  String name;
  Long type;
  String ownerId;
  Date lastTimeOnDevice;
  PetStateDTO state;
}
