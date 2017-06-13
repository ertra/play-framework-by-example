package controllers;

import play.mvc.*;

/**
 * This controller shows different ways how to work with query parameters in URL
 */
public class URLExamplesController extends Controller {

    /**
     * http://localhost:9000/urlWithoutParameter
     */
    public Result showURL() {
        return Results.TODO;
    }

    /**
     * http://localhost:9000/urlWithoutParameter/new
     */
    public Result showAnotherURL() {
        return Results.TODO;
    }

    /**
     * Reading one query parameter
     * http://localhost:9000/param1?name=Tomas
     */
    public Result getQuestyParameterExample1(String name){
        System.out.println("Name " + name);
        return ok("Hello " + name + " !");
    }

    /**
     * Reading one query parameter and sending the parameter value into the HTML page
     * http://localhost:9000/param2?name=Tomas
     */
    public Result getQuestyParameterExample2(String name){
        System.out.println("Name " + name);

        // calling the URLParamExample.scala.html page with name as the parameter
        return ok(views.html.URLParamExample.render(name));
    }

    /**
     * Reading part og the URL
     * http://localhost:9000/param3/12345
     */
    public Result getQuestyParameterExample3(Integer id){
        System.out.println("Id " + id);

        return ok("ök: " + id);
    }

    /**
     * Reading rest of the url after param4/
     * http://localhost:9000/param4/tex1/text2/text3
     */
    public Result getQuestyParameterExample4(String URLTest){
        System.out.println("URL " + URLTest);

        return ok("URL : " + URLTest);
    }

}


