package controllers;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Example of the OAuth
 */
public class OAuth2Controller extends Controller {

    // for test using localhost
    //String FacebookApiKey = "1378211368958616";
    //String FacebookApiSecret = "4d96ace911c6298cf491198f509855ff";
    //String FacebookCallback = "http://localhost:9000/oauth_callback/";

    // for test using Heroku
    String FacebookApiKey = "1121323201345943";
    String FacebookApiSecret = "6e76b61f38acd9fb6d215e414e14a07d";
    String FacebookCallback = "https://playframeworkbyexample.herokuapp.com/oauth_callback/";
    
    String FACEBOOK_PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.8/me";

    /**
     * This will redirec tto the Facebook, where user can confirm the rights for you.
     * http://localhost:9000/toFacebook
     */
    public Result redirectToFacebookOAuth() {

        OAuth20Service service = new ServiceBuilder()
                .apiKey(FacebookApiKey)
                .apiSecret(FacebookApiSecret)
                .scope("email")
                .callback(FacebookCallback)
                .build(FacebookApi.instance());

        System.out.println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl();
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);

        return redirect(authorizationUrl);
        //return ok(index.render());

    }

    public Result OAuthFacebook() throws InterruptedException, ExecutionException, IOException {
        String code = request().getQueryString("code");

        if (code == null || code.equals("")) {
            return ok("Facebook did not return code parameter");
        }

        OAuth20Service service = new ServiceBuilder()
                .apiKey(FacebookApiKey)
                .apiSecret(FacebookApiSecret)
                .scope("email")
                .callback(FacebookCallback)
                .build(FacebookApi.instance());

        OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();


        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        final OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response response = service.execute(request);
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());


        return ok("Here I am : " + response.getBody());
    }

}