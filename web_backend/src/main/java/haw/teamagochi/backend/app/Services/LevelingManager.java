package haw.teamagochi.backend.app.Services;

public class LevelingManager {

  /**
   *
   * @param xp the xp of the pet
   * @return array with length 2, position 0 is the level of the pet,
   * position 1 is the progress towards the next level in percent
   */
  public static int[] computeLevel(int xp){
    int[] returnValues = {0,0}; //pos 0 = level, pos 1 = progress;
    int xpNeeded = 100;
    while( xpNeeded<xp){
      returnValues[0] = returnValues[0]++;
      xp = xp - xpNeeded;
      xpNeeded = xpNeeded*(1 + (returnValues[0]/10));
    }//while
    returnValues[1] = xp / (xpNeeded/100);
    return returnValues;
  }//method


}
