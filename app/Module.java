import com.google.inject.AbstractModule;
import play.api.Configuration;
import play.api.Environment;
import services.OnStartupService;

import javax.inject.Inject;


public class Module extends AbstractModule {

    private final Environment environment;
    private final Configuration configuration;

    @Inject
    public Module(Environment environment, Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {

        // load this after the server starts only once
        bind(OnStartupService.class).asEagerSingleton();
    }

}
