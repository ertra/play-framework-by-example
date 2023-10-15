package controllers.actions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.libs.typedmap.TypedKey;
import play.mvc.Http;
import play.mvc.Result;
import utils.JWTUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecuredAPIAction extends play.mvc.Action.Simple {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SecuredAPIAction.class);

    // holding the parsed claims from the token
    public static final TypedKey<Map<String, Object>> CLAIMS = TypedKey.create("claims");

    // for parsing the Bearer token
    private static final Pattern CHALLENGE_PATTERN = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE);

    public CompletionStage<Result> call(Http.Request req) {

        Optional<String> headerWithToken = req.header("Authorization");
        String token;

        if (headerWithToken.isPresent()) {
            token = parseToken(headerWithToken.get());
        } else{
            return CompletableFuture.completedFuture(onUnauthorized(req));
        }

        // token was not parsed properly
        if (token == null) {
            return CompletableFuture.completedFuture(onUnauthorized(req));
        }

        Claims claims;

        try {
            claims = JWTUtils.getInstance().getJWTClaims(token);
        } catch (JwtException e){
            logger.error("JWT parsing error: " + e.toString() + " " + token);
            return CompletableFuture.completedFuture(onUnauthorized(req));
        }

        // return and add Claims into the attribute so we can read it in the following Action
        return delegate.call(req.addAttr(CLAIMS, claims));
    }

    private String parseToken(String input) {
        if (input == null) return null;

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

    private Result onUnauthorized(Http.Request req) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", "Not authorized");
        return unauthorized(Json.toJson(response));
    }

}
