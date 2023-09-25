import com.google.inject.AbstractModule;
import dao.BookDAO;
import dao.BookDAOImp;
import org.slf4j.LoggerFactory;
import services.BookService;
import services.BookServiceImp;
import services.OnStartupService;

import javax.inject.Inject;
import java.util.TimeZone;

public class Module extends AbstractModule {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    //private final Environment environment;
    //private final Config config;

    @Inject
    public Module(/*Environment environment, Config config*/) {
    //     this.environment = environment;
    //     this.config = config;
    }

    @Override
    protected void configure() {

        logger.info("-----------------------------------------------------------------------------");
        logger.info("Running Module.configure() on server startup, only 1x");

        // setting default timezone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // load this after the server starts and only once
        bind(OnStartupService.class).asEagerSingleton();

        bind(BookDAO.class).to(BookDAOImp.class);
        bind(BookService.class).to(BookServiceImp.class);
    }

}
