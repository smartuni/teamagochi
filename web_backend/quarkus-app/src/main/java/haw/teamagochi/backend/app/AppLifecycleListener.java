package haw.teamagochi.backend.app;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

/**
 * Event listener for Quarkus lifecycle events.
 */
@ApplicationScoped
public class AppLifecycleListener {

  private static final Logger LOGGER = Logger.getLogger(AppLifecycleListener.class);

  void onStart(@Observes StartupEvent event) {
    LOGGER.debug("The application is starting...");
  }

  void onStop(@Observes ShutdownEvent event) {
    LOGGER.debug("The application is stopping...");
  }
}
