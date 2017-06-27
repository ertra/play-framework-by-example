import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import dao.BookDAO;
import dao.BookDAOImp;
import services.BookServiceImp;
import services.*;
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

        bind(BookDAO.class).to(BookDAOImp.class);
        bind(BookService.class).to(BookServiceImp.class);
    }

}
