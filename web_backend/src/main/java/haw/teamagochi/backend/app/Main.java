package haw.teamagochi.backend.app;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * The application entrypoint.
 */
@QuarkusMain
public class Main {

  /**
   * Bootstrap Quarkus and run it until it stops.
   *
   * <p>It's not recommended to add business logic here because Quarkus has not been
   * set up yet and may run in a different ClassLoader. See {@link AppLifecycleListener}
   * instead or implement {@link io.quarkus.runtime.QuarkusApplication}.
   *
   * <p>See https://quarkus.io/guides/lifecycle
   *
   * @param args the command line arguments
   */
  public static void main(String... args) {
    Quarkus.run(args);
  }
}
