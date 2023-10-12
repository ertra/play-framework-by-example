package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.JwtException;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.JWTUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
     * 	  "email": "user@email.com",
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
        logger.info("Email: " + userLogin.getEmail());
        logger.info("Passwd: " + userLogin.getPassword());

        if (userLogin.getEmail() == null || userLogin.getPassword() == null) {
            return badRequest("Expecting email and password.");
        }

        // create JWT token
        String jws = null;
        try {
            jws = JWTUtils.getInstance().createJWTToken(userLogin.getEmail());
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
    public Result protectedRequestAPI(Http.Request request) throws NoSuchAlgorithmException {
        Optional<String> tokenHeader = request.header("Authorization");
        String token = "";

        if (tokenHeader.isPresent()) {
            logger.info("Authorization header: " + tokenHeader.get());
            token = tokenHeader.get().substring(7);
        }

        Map<String,Object> claims;

        try {
            claims = JWTUtils.getInstance().getJWTClaims(token);
        } catch (JwtException e){
            return badRequest(e.getMessage());
        }

        return ok("Email " + claims.get("email"));
    }
}

// DTO class to hold login information
class UserLogin {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
