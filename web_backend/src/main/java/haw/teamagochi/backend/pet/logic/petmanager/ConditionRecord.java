package haw.teamagochi.backend.pet.logic.petmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A summary of pet conditions.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ConditionRecord {
  private boolean evaluated;
  private Integer hungry;
  private Integer ill;
  private Integer bored;
  private Integer dirty;

  public ConditionRecord() {
    this.evaluated = false;
    this.hungry = 0;
    this.ill = 0;
    this.bored = 0;
    this.dirty = 0;
  }
}
