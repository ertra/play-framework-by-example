package controllers;


import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.CookieBuilder;
import play.mvc.Result;
import views.html.index;

import java.time.Duration;

public class CookieController extends Controller {

    /**
     * Set the Cookie name = aaa , value = bbb
     */
    public Result setCookie() {

        Http.Cookie cookie = Http.Cookie.builder("aaa", "bbb")
                .withMaxAge(Duration.ofDays(30))
                .build();

        response().setCookie(cookie);
        return ok("Cookie name 'aaa' set to value 'bbb'").as("text/html");
    }

    /**
     * Read Cookie of name aaa
     */
    public Result readCookie() {
        Http.Cookie cookie = request().cookie("aaa");

        if (cookie == null) {
            return ok("Cookie <b>'aaa'</b> doesnt exist ").as("text/html");
        } else {
            return ok("Cookie <b>'aaa'</b> value: " + cookie.value()).as("text/html");
        }
    }

    /**
     * Invalidate the Cookie
     */
    public Result deleteCookie() {
        response().discardCookie("aaa");
        return ok("Cookie aaa deleted").as("text/html");
    }
}
