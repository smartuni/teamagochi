package haw.teamagochi.backend.general.security;

import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Utilities for quarkus security.
 */
public class SecurityUtil {

  private static final String MOCK_UID = "96daba6c-00ae-4ddb-bea5-e9c0cdb5a459"; // testuser01

  // Set true if Keycloak is not enabled
  private static final boolean USE_MOCK_UID = !ConfigProvider.getConfig().getValue("keycloak.enabled", Boolean.class);

  /**
   * Get keycloak user id from quarkus security context.
   *
   * @param identity provided by quarkus security
   * @return the id (which is a UUID)
   */
  public static String getExternalUserId(SecurityIdentity identity) {
    if (USE_MOCK_UID) { return MOCK_UID; }

    JsonWebToken accessToken = (JsonWebToken) identity.getPrincipal();
    if (accessToken == null
        || accessToken.getSubject() == null
        || accessToken.getSubject().isEmpty()) {
      throw new UnauthorizedException();
    }
    return accessToken.getSubject();
  }
}
