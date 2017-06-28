package controllers;

import models.Book;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import services.BookService;
import services.BookServiceImp;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Exampe how to get data from database
 */
public class DatabaseExampleController extends Controller {

    @Inject
    BookService bookService;

    /**
     * get all books in the table Book
     *
     * @return all books in the table Book
     * @throws SQLException
     */
    @Transactional(readOnly = true)
    public Result getBooks() throws SQLException {
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
    @Transactional
    public Result insertBook() {

        String name = request().getQueryString("name");
        String author = request().getQueryString("author");

        if (name == null || author == null) {
            return badRequest("Some parameters are missing");
        }

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);

        bookService.insertBook(book);

        return ok("Book inserted " + name + " - " + author);
    }

}
