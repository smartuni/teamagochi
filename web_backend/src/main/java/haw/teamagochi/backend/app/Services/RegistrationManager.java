package haw.teamagochi.backend.app.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistrationManager {

  private HashMap<String, Long> devicesToBeRegistered;

  RegistrationManager(){
    devicesToBeRegistered = new HashMap<>();
  }


  /**
   *
   * @param deviceIdentifyer the Identifyer of the Device
   * @return the RegistrationKey to be displayed on the Device
   */
  public String addNewWaitingDevice(long deviceIdentifyer){
    if(!devicesToBeRegistered.containsValue(deviceIdentifyer)){
      String key = generateNewKey();
      devicesToBeRegistered.put(key, deviceIdentifyer);
      return key;
    }//if
    String key = "";
    for(Map.Entry<String, Long> entry: devicesToBeRegistered.entrySet()) {
      if(entry.getValue() == deviceIdentifyer) {
        key = entry.getKey();
        break; //for
      }//if
    }//for
    return key;
  }//method


  /**
   *
   * @param key the key assosiated with the device, a String
   * @return the deviceID, or -1 if key not found
   */
  public long getDevice(String key){
    if(devicesToBeRegistered.containsKey(key)){
      long deviceID = devicesToBeRegistered.get(key);
      devicesToBeRegistered.remove(key, deviceID);
      return deviceID;
    }
    return -1l;
  }


  //Zugriffbeschänken = 1 gleichzeitig
  private String generateNewKey(){
    Random rand = new Random();
    StringBuilder st = new StringBuilder("");
    String retu;
    while(true){
      for(int i = 0; i<8;i++){
        int tmp = rand.nextInt(90 - 65) + 65; //65 bis 90
        st.append((char) tmp);
      }//for
      retu = st.toString();
      if(!devicesToBeRegistered.containsKey(retu)) break;
    }
    return retu;
  }//method

}
