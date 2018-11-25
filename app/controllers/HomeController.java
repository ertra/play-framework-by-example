package controllers;

import akka.stream.Materializer;
import akka.util.ByteString;
import com.typesafe.config.Config;
import controllers.actions.AuthActionExample;
import play.cache.SyncCacheApi;
import play.mvc.*;

import scala.compat.java8.FutureConverters;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import views.html.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private SyncCacheApi cache;

    @Inject
    private Config config;

    @Inject
    URLExamplesController urlExampleContoller;

    @Inject
    Materializer materializer;

    public Result index() throws Exception {

        // other options form the official documentation

        //Result ok = ok("Hello world!");
        //Result notFound = notFound();
        //Result pageNotFound = notFound("<h1>Page not found</h1>").as("text/html");
        //Result badRequest = badRequest(views.html.form.render(formWithErrors));
        //Result oops = internalServerError("Oops");
        //Result anyStatus = status(488, "Strange response type");


        // how to do a redirect
        //return redirect("/user/home");


        // example of internally colling another URL

        /*

        Result result = urlExampleContoller.getQuestyParameterExample2("test 123");

        FiniteDuration finiteDuration = Duration.create(5000, TimeUnit.MILLISECONDS);
        byte[] body = Await.result(
                FutureConverters.toScala(result.body().consumeData(materializer)), finiteDuration).toArray();
        String outputOfAnotherController = new String(body);

        */


        // reading from application.conf file
        String configParam = config.getString("demoapp.config.example");

        return ok(index.render(configParam));

    }

    @With(AuthActionExample.class)
    public  Result dashboard() {
        String user = (String) ctx().args.get("user");
        return ok("User dashboard: " + user);
    }

    public  Result login() {
        return ok("Please login");
    }

}
