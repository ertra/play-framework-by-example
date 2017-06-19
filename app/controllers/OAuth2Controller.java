package controllers;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.plus.Plus;
import play.api.Play;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Example of the OAuth
 * See Goolge scopes https://developers.google.com/identity/protocols/googlescopes
 * See Facebook scopes https://developers.facebook.com/docs/facebook-login/permissions/
 */
public class OAuth2Controller extends Controller {

    // for test using localhost

    //String FacebookApiKey = "1378211368958616";
    //String FacebookApiSecret = "4d96ace911c6298cf491198f509855ff";
    //String FacebookCallback = "http://localhost:9000/oauth_callback_Facebook/";

    //String GoogleApiKey = "708028802616-t2bhap400h2j34lq3ehilna5ev8blsgr.apps.googleusercontent.com";
    //String GoogleApiSecret = "hxL8dkKZzslFBeSJHVZs2wi2";
    //String GoogleCallback =   "http://localhost:9000/oauth_callback_Google/";

    // for test using Heroku

    String FacebookApiKey = "1121323201345943";
    String FacebookApiSecret = "6e76b61f38acd9fb6d215e414e14a07d";
    String FacebookCallback = "https://playframeworkbyexample.herokuapp.com/oauth_callback_Facebook/";

    String GoogleApiKey = "708028802616-t2bhap400h2j34lq3ehilna5ev8blsgr.apps.googleusercontent.com";
    String GoogleApiSecret = "hxL8dkKZzslFBeSJHVZs2wi2";
    String GoogleCallback = "https://playframeworkbyexample.herokuapp.com/oauth_callback_Google/";


    // we will try to load some protected resources after OAuth
    String FACEBOOK_PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.8/me";
    String GOOGLE_PROTECTED_RESOURCE_URL = "https://www.googleapis.com/plus/v1/people/me";

    // What we ask Google for
    String GoogleScope = "profile email https://mail.google.com/ https://www.googleapis.com/auth/contacts.readonly";

    /**
     * This will redirec to the Facebook, where user can confirm the rights for you.
     * http://localhost:9000/toFacebook
     */
    public Result redirectToFacebookOAuth() {

        OAuth20Service service = new ServiceBuilder()
                .apiKey(FacebookApiKey)
                .apiSecret(FacebookApiSecret)
                .scope("email")         // add more stuff here if needed, this one will allow you to see email address
                .callback(FacebookCallback)
                .build(FacebookApi.instance());

        System.out.println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl();
        System.out.println("Got the Authorization URL!");
        System.out.println("We will redirect to it");
        System.out.println(authorizationUrl);

        // redirect to Facebook
        return redirect(authorizationUrl);
    }

    /**
     * Facebook will redirect back to this URL, with token in the parameter. From the code parameter, we will get user access token
     * /oauth_callback_Facebook/
     *
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
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

        // we get access token from the code
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();

        // we have the user access token at this point and we can read some resources

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = service.execute(request);
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());

        return ok("Here I am : " + response.getBody());
    }

    /**
     * This will redirec to the Google, where user can confirm the rights for you.
     * http://localhost:9000/toGoogle
     */
    public Result redirectToGoogleOAuth() {

        //String secretState = "secret" + new Random().nextInt(999_999);
        OAuth20Service service = new ServiceBuilder()
                .apiKey(GoogleApiKey)
                .apiSecret(GoogleApiSecret)
                .scope(GoogleScope)
                //.state(secretState)
                .callback(GoogleCallback)
                .build(GoogleApi20.instance());

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        //pass access_type=offline to get refresh token
        //https://developers.google.com/identity/protocols/OAuth2WebServer#preparing-to-start-the-oauth-20-flow
        Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("access_type", "offline");
        //force to reget refresh token (if usera are asked not the first time)
        additionalParams.put("prompt", "consent");
        String authorizationUrl = service.getAuthorizationUrl(additionalParams);
        System.out.println("Got the Authorization URL!");
        System.out.println("We will redirect to it");
        System.out.println(authorizationUrl);

        return redirect(authorizationUrl);

    }

    /**
     * Google will redirect back to this URL, with token in the parameter. From the code parameter, we will get user access token
     * /oauth_callback_Google/
     *
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public Result OAuthGoogle() throws InterruptedException, ExecutionException, IOException, GeneralSecurityException {

        String code = request().getQueryString("code");

        if (code == null || code.equals("")) {
            return ok("Google did not return code parameter");
        }

        OAuth20Service service = new ServiceBuilder()
                .apiKey(GoogleApiKey)
                .apiSecret(GoogleApiSecret)
                .scope(GoogleScope)
                //.state(secretState)
                .callback(GoogleCallback)
                .build(GoogleApi20.instance());

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");

        System.out.println("Refreshing the Access Token...");
        accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
        System.out.println("Refreshed the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();

        // getting some data
        final OAuthRequest request = new OAuthRequest(Verb.GET, GOOGLE_PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response response = service.execute(request);
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());

        // Reading Gmail labels
        /*
        System.out.println(" ------------------------------------------------------- Gmail labels example");

        // converting scribe accessToken to GoogleCredential
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getAccessToken());

        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport HTTP_TRANSPORT = HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Gmail gmail = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("Gmail API Java App").build();

        String user = "me";
        ListLabelsResponse listResponse =
                gmail.users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
        if (labels.size() == 0) {
            System.out.println("No labels found.");
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                System.out.printf("- %s\n", label.getName());
            }

        }
        */
        return ok("Here I am : " + accessToken +"\n\n" + response.getBody());
    }
}
