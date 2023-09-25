package controllers;

import models.Book;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.BookService;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Example how to get data from database
 */
public class DatabaseExampleController extends Controller {

    private final BookService bookService;

    @Inject
    public DatabaseExampleController(BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * get all books in the table Book
     *
     * @return all books in the table Book
     */
    public Result getBooks() throws SQLException, ExecutionException, InterruptedException {

        List<Book> books = bookService.getBooks();

        String tmp = "";
        for (Book book : books) {
            tmp = tmp + book.getId() + " " + book.getName() + " " + book.getAuthor() + "\n";
        }

        return ok("Size: " + books.size() + "\nBooks:\n" + tmp);
    }

    /**
     * insert Book into table Book, get parameters from URL
     */
    public Result insertBook(Http.Request request) throws ExecutionException, InterruptedException {

        Optional<String> name = request.queryString("name");
        Optional<String> author = request.queryString("author");

        if (name.isEmpty() || author.isEmpty()) {
            return badRequest("Some parameters are missing");
        }

        Book book = new Book();
        book.setName(name.get());
        book.setAuthor(author.get());

        bookService.insertBook(book);

        return ok("Book inserted " + name.get() + " - " + author.get());
    }

}
