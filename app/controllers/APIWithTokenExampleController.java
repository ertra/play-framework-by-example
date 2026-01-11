package controllers;

import controllers.actions.SecuredAPIAction;
import controllers.forms.UserLogin;
import jakarta.inject.Inject;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import utils.JWTUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Example how to use API with token
 */
public class APIWithTokenExampleController extends Controller {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(APIWithTokenExampleController.class);
    private final FormFactory formFactory;
    private final JWTUtils jwtUtils;

    @Inject
    public APIWithTokenExampleController(FormFactory formFactory, JWTUtils jwtUtils) {
        this.formFactory = formFactory;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Example of how handle login request to get the token
     *
     * curl --request POST \
     *   --url http://localhost:9000/loginAPI \
     *   --header 'Content-Type: application/json' \
     *   --data ' {
     * 	  "username": "user@email.com",
     *    "password": "111111"
     *  } '
     *
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result loginAPI(Http.Request request) {

        // will read json into our object, if we send proper Content-Type: application/json
        Form<UserLogin> form = formFactory.form(UserLogin.class).bindFromRequest(request);

        // return error, if the UserLogin was not filled properly
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        UserLogin userLogin = form.get();

        logger.debug("Login attempt for username: {}", userLogin.getUsername());

        // create JWT token
        String jws = jwtUtils.createJWTToken(userLogin.getUsername());

        HashMap<String, String> response = new HashMap<>();
        response.put("token", jws);
        return ok(Json.toJson(response));
    }

    /**
     * this API call needs token in header
     *
     * curl --request GET \
     *   --url http://localhost:9000/protectedRequestAPI \
     *   --header 'Authorization: Bearer <token generated from loginAPI>' \
     *   --header 'Content-Type: application/json' \
     *
     */
    @With(SecuredAPIAction.class)
    public Result protectedRequestAPI(Http.Request request) {

        // read Claims from attribute
        Map<String, Object> claims = request.attrs().get(SecuredAPIAction.CLAIMS);

        logger.info("Claims: " + claims.toString());

        HashMap<String, String> response = new HashMap<>();
        response.put("username", claims.get("username").toString());
        return ok(Json.toJson(response));
    }

}
