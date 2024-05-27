package haw.teamagochi.backend.user.logic;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class UserServiceTest {
  @Inject
  UserService userService;


}
