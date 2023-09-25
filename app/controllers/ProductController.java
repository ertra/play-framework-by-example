package controllers;

import models.Product;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Shows list of Prodcuts on the HTML page
 */
public class ProductController extends Controller {

    /**
     * Show ArrayList in scala template and iterate it
     * http://localhost:9000/showProducts
     */
    public Result showProducts() {
        // getting the list of Products
        List<Product> products = Product.findAll();
        return ok(views.html.showProducts.render(products));
    }

}
