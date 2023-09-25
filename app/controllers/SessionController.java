package controllers;

import controllers.actions.Secured;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Optional;

// tutorial how to use Flash for exchanging data
// https://www.playframework.com/documentation/2.9.x/JavaSessionFlash

public class SessionController extends Controller {

    final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionController.class);

    /**
     * Set the Session
     */
    public Result login(Http.Request request) {
        Http.Session session = new Http.Session();
        session = session.adding("user", "user@gmail.com");

        // creates new session, deleting old content
        return ok("Session user=user@gmail.com is set").withSession(session);

        // if you want to add to existing session, use
        // return ok("Session user=user@gmail.com is set").addingToSession(request,"user","user@gmail.com");
    }


    /**
     * Read the Session
     */
    public Result user(Http.Request request) {

        Optional<String> user = request.session().get("user");

        logger.info("Session isPresent: " + user.isPresent());

        if(user.isPresent()) {
            return ok("Hello " + user.get() + " session was found.");
        } else {
            return unauthorized("Oops, Session user doesnt exist.");
        }
    }

    /**
     * Secured by Secured.class and redirected if no session exists
     */
    @Security.Authenticated(Secured.class)
    public Result userLoginCheck(Http.Request request) {
        return ok("User session exists, so you see this page and you were not redirected by Secured.class");
    }

    /**
     * Remove the Session
     */
    public Result logout(Http.Request request) {

        // removes key from session
        return ok("Session user removed").removingFromSession(request,"user");

        // if you want to delete the whole session use
        // .withNewSession();
    }
}
