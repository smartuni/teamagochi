package haw.teamagochi.backend.pet.service.rest.v1.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A data transfer object (DTO) for pet state.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RegisterForReflection
public class PetStateDTO {
  Integer happiness;
  Integer wellbeing;
  Integer health;
  Integer hunger;
  Integer cleanliness;
  Integer fun;
  Integer xp;

// TODO
//  int color;
//  int level;
//  int xp;
//  int levelProgress;
//  Date lastTimeOnDevice;
}
