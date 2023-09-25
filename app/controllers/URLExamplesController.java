package controllers;

import org.slf4j.LoggerFactory;
import play.Logger;
import play.mvc.*;
import services.BookServiceImp;

import java.util.List;
import java.util.Optional;

/**
 * This controller shows different ways how to work with query parameters in URL
 */
public class URLExamplesController extends Controller {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    /**
     * http://localhost:9000/urlWithoutParameter
     */
    public Result showURL(Http.Request request) {
        return ok("Hello world!");
    }

    /**
     * http://localhost:9000/urlWithoutParameter/new
     */
    public Result showAnotherURL(Http.Request request) {
        return ok("Got request path: " + request.path());
    }

    /**
     * Reading one query parameter
     * http://localhost:9000/param1?name=Tomas
     */
    public Result getQuestyParameterExample1(String name){
        logger.info("Name " + name);
        return ok("Hello " + name + " !");
    }

    /**
     * Reading one query parameter and sending the parameter value into the HTML page
     * http://localhost:9000/param2?name=Tomas
     */
    public Result getQuestyParameterExample2(String name){
        logger.info("Name " + name);

        // calling the URLParamExample.scala.html page with name as the parameter
        return ok(views.html.URLParamExample.render(name));
    }

    /**
     * Reading part og the URL
     * http://localhost:9000/param3/12345
     */
    public Result getQuestyParameterExample3(Integer id){
        logger.info("Id " + id);

        return ok("ok: " + id);
    }

    /**
     * Reading rest of the url after param4/
     * http://localhost:9000/param4/tex1/text2/text3
     */
    public Result getQuestyParameterExample4(String URLTest, Http.Request request){
        logger.info("URL " + URLTest);
        Optional<String> queryString = request.queryString("param1");

        return ok("URL with multiple '/': " + URLTest + " and one query parameter: " + queryString.orElse(""));
    }

    public Result listItems(List<String> list){
        logger.info("Size of list: " + list.size());

        return ok("URL wih list: " + list.toString());
    }

}


