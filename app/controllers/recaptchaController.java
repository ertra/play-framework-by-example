package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.*;
import scala.concurrent.ExecutionContextExecutor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


import javax.inject.Inject;

import static play.data.Form.form;

/**
 * Play Framework Java reCaptcha implementation example as a Controller
 */

public class recaptchaController extends Controller {

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
    public Result formSubmit() throws IOException, InterruptedException, ExecutionException {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        // get parameter with code from reCaptcha, this is filled by Goolge js library automatically
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
                .setQueryParameter("secret", "6LfnWiYUAAAAABjt7L0l991PtmRW6enq0kIFQ1Ks")
                .setQueryParameter("response", code)
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

        // Alternative way, how to read it can be via: http://unirest.io/java.html

        // Alternative way how to send and get response from Google via HttpClient

        /*
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("secret", "6LfnWiYUAAAAABjt7L0l991PtmRW6enq0kIFQ1Ks"));
        params.add(new BasicNameValuePair("response", code));
        request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        // and we have in result the Google response
        // return ok("After the reCaptcha: \nName: " + name +"\nCode: " + code +"\nResult: " + result);

        */

    }

}
