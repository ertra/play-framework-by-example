import play.Environment;
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
    public Filters(SecurityHeadersFilter securityHeadersFilter, GzipFilter gzipFilter, Environment env) {
        super(securityHeadersFilter, gzipFilter);
        this.env = env;
    }

}
