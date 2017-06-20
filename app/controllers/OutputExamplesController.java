package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Product;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;


public class OutputExamplesController extends Controller {

    /**
     * render the json output
     * @return
     */
    public Result index() {

        Product p1 = new Product("111", "Jack", "Book about flowers",
                "description about book 111");
        Product p2 = new Product("112", "Tomas", "Book about cars",
                "description about book 112");

        ArrayList<Product> productList = new ArrayList();
        productList.add(p1);
        productList.add(p2);

        JsonNode json = Json.toJson(productList);

        Result jsonResult = ok(json);
        return jsonResult;
    }


    /**
     * show how to use tag
     *
     */
    public Result tagExample() {

        return ok(views.html.examplePage.render());
    }

    /**
     * show how to use template
     *
     */
    public Result templateExample() {

        return ok(views.html.pageExample2.render());
    }
}
