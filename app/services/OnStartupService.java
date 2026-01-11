package services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.LoggerFactory;
import play.Environment;
import play.inject.ApplicationLifecycle;

@Singleton
public class OnStartupService {

    //private final Config config;

    final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public OnStartupService(Environment environment, ApplicationLifecycle appLifecycle /*, Config config*/) {
        //this.config = config;

        // this is executed only once when the app starts
        logger.info("Java tmp dir: " + System.getProperty("java.io.tmpdir"));

        if (environment.isDev()) {
            logger.info(" ----------- App is starting in dev mode\n");
        } else {
            logger.info(" ----------- App is starting in production mode\n");
        }
    }

}
