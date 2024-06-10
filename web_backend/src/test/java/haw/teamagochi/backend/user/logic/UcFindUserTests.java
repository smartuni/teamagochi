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
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link UcFindUser}.
 */
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UcFindUserTests {

  @Inject
  UcFindUser ucFindUser;

  @Inject
  UserRepository userRepository;

  @AfterEach
  @Transactional
  public void afterEach() {
    userRepository.deleteAll();
  }

  @Test
  @Transactional
  void testFindById() {
    // Given
    UserEntity entity = new UserEntity();
    userRepository.persist(entity);

    // When
    UserEntity fetchedUser = ucFindUser.find(entity.getId());

    // Then
    assertEquals(entity.getId(), fetchedUser.getId());
  }

  @Test
  @Transactional
  void testFindByExternalId() {
    // Given
    UUID uuid = UUID.fromString("2573f738-8765-4484-a985-c3214ed05f6f");
    UserEntity entity = new UserEntity();
    entity.setExternalID(uuid);
    userRepository.persist(entity);

    // When
    UserEntity fetchedUser = ucFindUser.find(entity.getId());

    // Then
    assertEquals(entity.getId(), fetchedUser.getId());
  }
}