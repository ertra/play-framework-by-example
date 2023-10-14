package controllers.actions;

import io.jsonwebtoken.JwtException;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import utils.JWTUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example how to deal with pages after the login
 * use annotation @Security.Authenticated(SecuredAPI.class) for controllers after the login
 */

public class SecuredAPI extends Security.Authenticator {

    public final static String USERNAME = "username";
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SecuredAPI.class);

    // for reading the Bearer token
    private static final Pattern CHALLENGE_PATTERN = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE);

    @Override
    public Optional<String> getUsername(Http.Request req) {

        Optional<String> tokenHeader = req.header("Authorization");
        String token = "";

        if (tokenHeader.isPresent()) {

            // get the part after Bearer
            token = parseToken(tokenHeader.get());
        } else{
            return Optional.empty();
        }

        // token was not parsed properly
        if (token == null) {
            return Optional.empty();
        }

        Map<String,Object> claims;

        try {
            claims = JWTUtils.getInstance().getJWTClaims(token);
            logger.info("Claims in the token: " + claims.toString());
        } catch (JwtException e){
            logger.error(e.toString() + " " + token);
            return Optional.empty();
        }

        return Optional.ofNullable((String) claims.get(USERNAME));
    }

    @Override
    public Result onUnauthorized(Http.Request req) {

        HashMap<String, String> response = new HashMap<>();
        response.put("error", "Not authorized");

        return unauthorized(Json.toJson(response));
    }

    private String parseToken(String input) {
        if (input == null) {
            return null;
        }

        // First, check whether the input matches the pattern
        // "Bearer {access-token}".
        Matcher matcher = CHALLENGE_PATTERN.matcher(input);

        // If the input matches the pattern.
        if (matcher.matches()) {
            return matcher.group(1);
        }
        else {
            return null;
        }
    }

}
