package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.leshanclient.datatypes.rest.ClientDto;
import java.util.List;

/**
 * Operations to find clients.
 */
public interface UcFindLeshanClient {

  /**
   * Get registered clients.
   *
   * @return a list of currently registered clients.
   */
  List<ClientDto> getClients();
}
