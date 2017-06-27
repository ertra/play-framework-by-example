import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import services.OnStartupService;

import javax.inject.Inject;

public class Module extends AbstractModule {

    //private final Environment environment;
    //private final Config config;

    @Inject
    public Module(/*Environment environment, Config config*/) {
    //     this.environment = environment;
    //     this.config = config;
    }

    @Override
    protected void configure() {

        // load this after the server starts only once
        bind(OnStartupService.class).asEagerSingleton();
    }

}
