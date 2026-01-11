package controllers;

import com.typesafe.config.Config;
import jakarta.inject.Inject;
import play.cache.SyncCacheApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private SyncCacheApi cache;

    @Inject
    private Config config;

    public Result index(Http.Request request) throws Exception {

        // other options form the official documentation

        //Result ok = ok("Hello world!");
        //Result notFound = notFound();
        //Result pageNotFound = notFound("<h1>Page not found</h1>").as("text/html");
        //Result badRequest = badRequest(views.html.form.render(formWithErrors));
        //Result oops = internalServerError("Oops");
        //Result anyStatus = status(488, "Strange response type");

        // how to do a redirect
        //return redirect("/user/home");
        //return temporaryRedirect("/user/home");

        // reading from application.conf file
        String configParam = config.getString("demoapp.config.example");

        //usage of Flash, message is generates in Secured.onUnauthorized()
        Http.Flash flash = request.flash();

        return ok(index.render(configParam,flash));
    }

}
