package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * Play Framework Java reCaptcha implementation example as a Controller
 */

public class RecaptchaExampleController extends Controller {

    @Inject
    FormFactory formFactory;

    @Inject
    WSClient ws;

    /**
     * Show html page with simple form and reCaptcha
     *
     * also see header contentSecurityPolicy = null
     * to display the captcha
     */
    public Result index() {
        return ok(views.html.recaptcha.render());
    }


    /**
     * Process the reCaptcha and output success or error
     */
    public Result formSubmit(Http.Request request) throws IOException, InterruptedException, ExecutionException {
        DynamicForm requestData = formFactory.form().bindFromRequest(request);
        // get parameter with code from reCaptcha, this is filled by Google js library automatically
        String code = requestData.get("g-recaptcha-response");
        // get name from the our normal form
        String name = requestData.get("name");


        // json to be send to Google
        //JsonNode authJson = Json
        //            .newObject()
        //            .put("secret", "6LfnWiYUAAAAABjt7L0l991PtmRW6enq0kIFQ1Ks")
        //            .put("response", code);


        // call the Google WS
        CompletionStage<WSResponse> responsePromise = ws.url("https://www.google.com/recaptcha/api/siteverify")
                .addQueryParameter("secret", "6LfnWiYUAAAAABjt7L0l991PtmRW6enq0kIFQ1Ks")
                .addQueryParameter("response", code)
                .setMethod("POST")
                .get();
                //.post(authJson);  ... we dont use this, but could be used with authJson


        // force it to do the request and get response
        CompletableFuture<WSResponse> completableFuture = responsePromise.toCompletableFuture();

        JsonNode responseFromGoogle = completableFuture.get().asJson();
        System.out.println("Response from Google: " + responseFromGoogle.toString());

        // should be "true" or "false"
        String isSuccess = responseFromGoogle.findPath("success").asText();

        if (isSuccess.equals("true")) {
            return ok("reCaptcha verification OK - " + responseFromGoogle.toString());
        } else {
            return ok("reCaptcha verification was not successfull - " + responseFromGoogle.toString());
        }

    }

}
