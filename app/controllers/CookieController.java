package controllers;


import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.CookieBuilder;
import play.mvc.Result;
import views.html.index;

public class CookieController extends Controller {

    /**
     * Set the Cookie name = aaa , value = bbb
     */
    public Result setCookie() {

        Http.Cookie cookie = Http.Cookie.builder("aaa", "bbb").build();

        response().setCookie(cookie);
        return ok("Cookie name 'aaa' set to value 'bbb'");
    }

    /**
     * Read Cookie of name aaa
     */
    public Result readCookie() {
        Http.Cookie cookie = request().cookie("aaa");

        if (cookie == null) {
            return ok("Cookie 'aaa' doesnt exist ");
        } else {
            return ok("Cookie 'aaa' value: " + cookie.value());
        }
    }

    /**
     * Invalidate the Cookie
     */
    public Result deleteCookie() {
        response().discardCookie("aaa");
        return ok("Cookie aaa deleted");
    }
}
