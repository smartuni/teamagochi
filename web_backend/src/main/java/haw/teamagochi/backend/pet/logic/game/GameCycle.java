package haw.teamagochi.backend.pet.logic.game;

/**
 * Game cycle, tick, clock, runner, whatever you name it.
 */
public interface GameCycle {

  /**
   * Runs the Game Cycle and therefore updates the state-values of the pet's.
   */
  void doWork();

  /**
   * Setter for stopRequest.
   */
  void setStopRequested(boolean value);

  /**
   * Check if it's working.
   */
  boolean isStopped();
}
