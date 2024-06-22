package haw.teamagochi.backend.device.logic.registrationmanager;

/**
 * Defines a registration manager.
 *
 * <p>A registration manager stores devices which registered to the LwM2M server but
 * have never been registered with the backend application.
 */
public interface RegistrationManager {

  /**
   * Register a client.
   *
   * @param registrationCode the key associated with the client
   * @return the "Endpoint Client Name" of a LwM2M client
   */
  String registerClient(String registrationCode);

  /**
   * Add a device.
   *
   * @param endpoint is the "Endpoint Client Name" of a LwM2M client
   */
  void addClient(String endpoint);

  /**
   * Remove a device.
   *
   * @param endpoint is the "Endpoint Client Name" of a LwM2M client
   */
  void removeClient(String endpoint);

  /**
   * Remove outdated clients.
   */
  void removeOutdatedClients();

  /**
   * Amount of clients waiting available for registration.
   *
   * @return the amount of clients
   */
  int size();

  /**
   * Clear all caches.
   */
  void clearCache();
}
