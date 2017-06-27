package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import services.BookService;
import services.BookServiceImp;

import javax.inject.Inject;
import java.sql.SQLException;

/**
 *
 */
public class DatabaseExampleController  extends Controller {

    //private static BookService b = new BookServiceImp();

    @Inject
    BookService b;

    //@T3ransactional(readOnly=true)
    public Result index() throws SQLException {
        b.getBook();
        return ok("Test 1 ");
    }

}
