package controllers;

import org.apache.commons.logging.Log;
import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * GET   /   controllers.HomeController.index
     * http://localhost:9000/
     */
    public Result index() {
        return ok(index.render());
    }

}
