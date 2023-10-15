package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.actions.SecuredAPIAction;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import utils.JWTUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * Example how to use API with token
 */
public class APIWithTokenExampleController extends Controller {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(APIWithTokenExampleController.class);

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
    public Result loginAPI(Http.Request request) throws NoSuchAlgorithmException {

        JsonNode jsonNode = request.body().asJson();

        if (jsonNode == null) {
            return badRequest("Expecting Json data");
        }

        UserLogin userLogin = Json.fromJson(jsonNode, UserLogin.class);
        logger.info("Username: " + userLogin.getUsername());
        logger.info("Passwd: " + userLogin.getPassword());

        if (userLogin.getUsername() == null || userLogin.getPassword() == null) {
            return badRequest("Expecting username and password.");
        }

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

// DTO class to hold login information
class UserLogin {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
