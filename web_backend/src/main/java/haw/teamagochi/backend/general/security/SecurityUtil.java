package haw.teamagochi.backend.general.security;

import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Utilities for quarkus security.
 */
public class SecurityUtil {

  private static final boolean USE_DEBUG_UID = true;
  private static final String DEBUG_UID = "96daba6c-00ae-4ddb-bea5-e9c0cdb5a459"; // testuser01

  /**
   * Get keycloak user id from quarkus security context.
   *
   * @param identity provided by quarkus security
   * @return the id (which is a UUID)
   */
  public static String getExternalUserId(SecurityIdentity identity) {
    if (USE_DEBUG_UID) { return DEBUG_UID; }

    JsonWebToken accessToken = (JsonWebToken) identity.getPrincipal();
    if (accessToken == null
        || accessToken.getSubject() == null
        || accessToken.getSubject().isEmpty()) {
      throw new UnauthorizedException();
    }
    return accessToken.getSubject();
  }
}
