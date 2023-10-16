package controllers;

import controllers.actions.SecuredAPIAction;
import controllers.forms.UserLogin;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import utils.JWTUtils;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * Example how to use API with token
 */
public class APIWithTokenExampleController extends Controller {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(APIWithTokenExampleController.class);

    @Inject
    FormFactory formFactory;

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
    public Result loginAPI(Http.Request request) throws NoSuchAlgorithmException {

        // will read json into our object, if we send proper Content-Type: application/json
        Form<UserLogin> form = formFactory.form(UserLogin.class).bindFromRequest(request);

        // return error, if the UserLogin was not filled properly
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        UserLogin userLogin = form.get();

        logger.info("Username: " + userLogin.getUsername());
        logger.info("Passwd: " + userLogin.getPassword());

        // create JWT token
        String jws = null;
        try {
            jws = JWTUtils.getInstance().createJWTToken(userLogin.getUsername());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

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
