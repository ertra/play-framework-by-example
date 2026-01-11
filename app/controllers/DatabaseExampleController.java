package controllers;

import jakarta.inject.Inject;
import models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.BookService;

import java.util.List;
import java.util.Optional;

/**
 * Example how to get data from database
 * Similar example https://petrepopescu.tech/2021/02/building-a-rest-api-in-play-framework/
 */
public class DatabaseExampleController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseExampleController.class);
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
    public Result getBooks() {
        try {
            List<Book> books = bookService.getBooks();

            StringBuilder sb = new StringBuilder();
            for (Book book : books) {
                sb.append(book.getId()).append(" ")
                  .append(book.getName()).append(" ")
                  .append(book.getAuthor()).append("\n");
            }

            return ok("Size: " + books.size() + "\nBooks:\n" + sb);
        } catch (Exception e) {
            logger.error("Failed to retrieve books", e);
            return internalServerError("Failed to retrieve books");
        }
    }

    /**
     * insert Book into table Book, get parameters from URL
     */
    public Result insertBook(Http.Request request) {

        Optional<String> name = request.queryString("name");
        Optional<String> author = request.queryString("author");

        if (name.isEmpty() || author.isEmpty()) {
            return badRequest("Some parameters are missing");
        }

        try {
            Book book = new Book();
            book.setName(name.get());
            book.setAuthor(author.get());

            bookService.insertBook(book);

            return ok("Book inserted " + name.get() + " - " + author.get());
        } catch (Exception e) {
            logger.error("Failed to insert book", e);
            return internalServerError("Failed to insert book");
        }
    }

}
