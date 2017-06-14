import com.google.inject.AbstractModule;
import services.OnStartupService;


public class GlobalConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {

        // load this after the server starts
        bind(OnStartupService.class).asEagerSingleton();
    }


}
