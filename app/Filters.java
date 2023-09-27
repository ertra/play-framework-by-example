import filters.LoggingFilter;
import play.Environment;
import play.filters.csrf.CSRFFilter;
import play.filters.gzip.GzipFilter;
import play.filters.headers.SecurityHeadersFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

public class Filters extends DefaultHttpFilters {

    // this is applied to every request

    @Inject
    private Environment env;

    //@Inject
    //public Filters(SecurityHeadersFilter securityHeadersFilter, CORSFilter corsFiler, CSRFFilter csrfFilter, GzipFilter gzipFilter) {
    //    super(securityHeadersFilter, corsFiler, csrfFilter, gzipFilter);
    //}

    @Inject
    public Filters(CSRFFilter csrfFilter, LoggingFilter loggingFilter, SecurityHeadersFilter securityHeadersFilter, GzipFilter gzipFilter, Environment env) {
        super(csrfFilter, loggingFilter, securityHeadersFilter, gzipFilter);
        this.env = env;
    }

}
