package controllers;

import controllers.actions.AuthActionExample;
import org.apache.commons.logging.Log;
import play.cache.CacheApi;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private CacheApi cache;

    @Inject
    public HomeController(CacheApi cache) {
        this.cache = cache;
    }

    public Result index() {

        // other options form the official documentation

        //Result ok = ok("Hello world!");
        //Result notFound = notFound();
        //Result pageNotFound = notFound("<h1>Page not found</h1>").as("text/html");
        //Result badRequest = badRequest(views.html.form.render(formWithErrors));
        //Result oops = internalServerError("Oops");
        //Result anyStatus = status(488, "Strange response type");


        // how to do a redirect
        //return redirect("/user/home");

        // normal usage
        return ok(index.render());

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
