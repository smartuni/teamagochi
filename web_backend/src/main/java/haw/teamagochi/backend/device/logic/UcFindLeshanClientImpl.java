package haw.teamagochi.backend.device.logic;

import haw.teamagochi.backend.device.logic.clients.rest.LeshanClientRestclient;
import haw.teamagochi.backend.leshanclient.datatypes.rest.ClientDto;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Default implementation for {@link UcFindLeshanClient}.
 */
@ApplicationScoped
public class UcFindLeshanClientImpl implements UcFindLeshanClient {

  @RestClient
  private LeshanClientRestclient restClient;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ClientDto> getClients() {
    return restClient.getClients().stream().toList();
  }
}
