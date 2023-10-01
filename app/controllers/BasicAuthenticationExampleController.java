package controllers;

import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Base64;
import java.util.Optional;

public class BasicAuthenticationExampleController extends Controller {


    final org.slf4j.Logger logger = LoggerFactory.getLogger(FormExampleController.class);

    /**
     * Basic Authentication example
     */
    public Result authenticate(Http.Request request) {

        // read the header
        Optional<String> s = request.header("Authorization");

        logger.info("Authorization header: " + s.orElse("is not present"));

        if (s.isEmpty()){
            // return error, if header is not present
            return unauthorized().withHeader("WWW-Authenticate","Basic realm=\"Access\"");
        }

        String base64decoded = new String(Base64.getDecoder().decode(s.get().split(" ")[1]));
        logger.info("Decoded header: " + base64decoded);

        if (base64decoded.equals("admin:123")){
            return ok("user name and password are valid. Access granted");
        } else{
            // ask for username and password again
            return unauthorized().withHeader("WWW-Authenticate","Basic realm=\"Access\"");
        }

    }

}
