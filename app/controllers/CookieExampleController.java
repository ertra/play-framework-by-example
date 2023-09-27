package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.time.Duration;
import java.util.Optional;

public class CookieExampleController extends Controller {

    /**
     * Set the Cookie name = aaa , value = bbb
     */
    public Result setCookie() {

        Http.Cookie cookie = Http.Cookie.builder("aaa", "bbb")
                .withMaxAge(Duration.ofDays(30))
                .build();

        return ok("Cookie name 'aaa' set to value 'bbb'").withCookies(cookie);
    }

    /**
     * Read Cookie of name aaa
     */
    public Result readCookie(Http.Request request) {

        // Read Cookie by name 'aaa'
        Optional<Http.Cookie> cookie = request.cookie("aaa");

        if (cookie.isEmpty()) {
            return ok("Cookie doesn't exist.");
        } else {
            return ok("Cookie 'aaa' value: " + cookie.get().value());
        }
    }

    /**
     * Invalidate the Cookie
     */
    public Result deleteCookie() {
        return ok("Cookie aaa was deleted").discardingCookie("aaa");
    }
}
