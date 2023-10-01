package controllers.actions;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Example how to deal with pages after the login
 * use annotation @Security.Authenticated(Secured.class) for controllers after the login
 */

public class Secured extends Security.Authenticator {

    public final static String SESSION = "user";

    @Inject
    public Secured(){
    }

    @Override
    public Optional<String> getUsername(Http.Request req) {
        return req.session().get(SESSION);
    }

    @Override
    public Result onUnauthorized(Http.Request req) {

        return redirect(controllers.routes.HomeController.index()).
                flashing("danger",  "You need to login before accessing the application.");

        // alternatively, you can just return
        //return unauthorized();
    }

    public static boolean isLoggedIn(Http.Request req) {
        Optional<String> login = req.session().get(SESSION);
        return login.isPresent();
    }

}
