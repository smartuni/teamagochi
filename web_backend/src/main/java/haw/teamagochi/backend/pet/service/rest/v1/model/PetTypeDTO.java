package haw.teamagochi.backend.pet.service.rest.v1.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A data transfer object (DTO) for pet types.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RegisterForReflection
public class PetTypeDTO {
  Integer id;
  String name;
}
