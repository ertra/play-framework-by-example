package controllers;


import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.CookieBuilder;
import play.mvc.Result;
import views.html.index;

public class CookieController extends Controller {

    public Result setCookie() {
        Http.CookieBuilder cookieBuilder = Http.Cookie.builder("aaa", "bbb");
        Http.Cookie cookie = cookieBuilder.build();
        response().setCookie(cookie);
        return ok("Cookie aaa set to bbb");
    }

    public Result readCookie() {
        Http.Cookie cookie = request().cookie("aaa");
        if (cookie == null) {
            return ok("Cookie aaa doesnt exist ");

        } else {
            return ok("Cookie aaa value : " + cookie.value());
        }
    }

    public Result deleteCookie() {
        response().discardCookie("aaa");
        return ok("Cookie aaa deleted");
    }
}
