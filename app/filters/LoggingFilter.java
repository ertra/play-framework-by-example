package filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.EssentialAction;
import play.mvc.EssentialFilter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Executor;

@Singleton
public class LoggingFilter extends EssentialFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    private final Executor exec;

    @Inject
    public LoggingFilter(Executor exec) {
        this.exec = exec;
    }

    @Override
    public EssentialAction apply(EssentialAction next) {
        return EssentialAction.of(request -> {
                    long startTime = System.currentTimeMillis();
                    return next.apply(request).map(result ->
                                {
                                    long endTime = System.currentTimeMillis();
                                    long requestTime = endTime - startTime;
                                    log.info(
                                            "{} {} took {}ms and returned {}",
                                            request.method(),
                                            request.uri(),
                                            requestTime,
                                            result.status());

                                    return result.withHeader("Request-Time", "" + requestTime);
                                }
                                , exec);
                }
               /* {
                    long endTime = System.currentTimeMillis();
                    long requestTime = endTime - startTime;

                    log.info(
                            "{} {} took {}ms and returned {}",
                            request.method(),
                            //requests.uri(),
                            requestTime,
                            result.status());

                     return result.withHeader("Request-Time", "" + requestTime);
                }*/
        );
    }

    /*
    @Override
    public CompletionStage<Result> apply(
            Function<Http.RequestHeader, CompletionStage<Result>> nextFilter,
            Http.RequestHeader requestHeader) {
        long startTime = System.currentTimeMillis();
        return nextFilter
                .apply(requestHeader)
                .thenApply(
                        result -> {
                            long endTime = System.currentTimeMillis();
                            long requestTime = endTime - startTime;

                            log.info(
                                    "{} {} took {}ms and returned {}",
                                    requestHeader.method(),
                                    requestHeader.uri(),
                                    requestTime,
                                    result.status());

                            return result.withHeader("Request-Time", "" + requestTime);
                        });
    }
     */

}
