package filters;

import play.filters.cors.CORSFilter;
import play.filters.gzip.GzipFilter;
import play.http.DefaultHttpFilters;
import play.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import javax.inject.Inject;

public class Filters extends DefaultHttpFilters {

    // this is applied to every request

    @Inject
    public Filters(CORSFilter corsFiler, CSRFFilter csrfFilter, GzipFilter gzipFilter) {
        super(corsFiler, csrfFilter, gzipFilter);
    }
}
