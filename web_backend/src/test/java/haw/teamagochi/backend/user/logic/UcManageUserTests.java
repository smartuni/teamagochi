package haw.teamagochi.backend.user.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import haw.teamagochi.backend.user.dataaccess.model.UserEntity;
import haw.teamagochi.backend.user.dataaccess.repository.UserRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link UcManageUser}.
 */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UcManageUserTests {

  @Inject
  UcManageUser ucManageUser;

  @Inject
  UserRepository userRepository;

  @BeforeEach
  public void beforeEach() {}

  @AfterEach
  @Transactional
  public void afterEach() {
    userRepository.deleteAll();
  }

  @Test
  void testCreateUser() {
    // Given
    UUID uuid = UUID.fromString("2573f738-8765-4484-a985-c3214ed05f6f");

    // When
    UserEntity user = ucManageUser.create(uuid.toString());

    // Then
    UserEntity fetchedUser = userRepository.findByExternalId(uuid);
    assertEquals(user.getId(), fetchedUser.getId());
  }
}
