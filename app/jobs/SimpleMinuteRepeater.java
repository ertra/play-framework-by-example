package jobs;

import akka.actor.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Singleton;
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

        this.initialize();
    }

    private void initialize() {
        this.actorSystem
                .scheduler()
                .scheduleAtFixedRate(
                        Duration.create(10, TimeUnit.SECONDS), // initialDelay
                        Duration.create(1, TimeUnit.MINUTES), // interval
                        () -> {
                            logger.info("Ping, once per minute.");
                        },
                        this.executionContext);
    }

}
