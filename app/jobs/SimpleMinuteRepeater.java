package jobs;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.pekko.actor.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

@Singleton
public class SimpleMinuteRepeater {

    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;

    private static final Logger logger = LoggerFactory.getLogger(SimpleMinuteRepeater.class);

    @Inject
    public SimpleMinuteRepeater(ActorSystem actorSystem, ExecutionContext executionContext) {
        this.actorSystem = actorSystem;
        this.executionContext = executionContext;

        logger.info("SimpleMinuteRepeater initialized.");

        initialize();
    }

    private void initialize() {

        actorSystem.scheduler().scheduleAtFixedRate(
                Duration.create(10, TimeUnit.SECONDS),  // initial delay
                Duration.create(1, TimeUnit.MINUTES),   // interval
                () -> logger.info("Ping, once per minute."),
                executionContext
        );
    }

}
