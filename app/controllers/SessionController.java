package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.CookieBuilder;
import play.mvc.Result;

// tutorial how to use Flash for exchanging data
// https://www.playframework.com/documentation/2.5.x/JavaSessionFlash

public class SessionController extends Controller {

    /**
     * Set the Session
     */
    public Result login() {
        session("user", "user@gmail.com");
        return ok("Session user=user@gmail.com is set");
    }

    /**
     * Read the Session
     */
    public Result user() {
        String user = session("user");
        if(user != null) {
            return ok("Hello " + user);
        } else {
            return unauthorized("Oops, Session user doesnt exist");
        }
    }

    /**
     * Remove the Session
     */
    public Result logout() {
        session().remove("user");
        return ok("Session user removed");
    }
}
