package haw.teamagochi.backend.pet.logic.petmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A summary of pet interactions.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class InteractionRecord {
  private boolean evaluated;
  private Integer feed;
  private Integer medicate;
  private Integer play;
  private Integer clean;

  public InteractionRecord() {
    this.evaluated = false;
    this.feed = 0;
    this.medicate = 0;
    this.play = 0;
    this.clean = 0;
  }
}
