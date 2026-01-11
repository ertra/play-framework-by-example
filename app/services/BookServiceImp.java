package services;

import dao.BookDAO;
import jakarta.inject.Inject;
import models.Book;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookServiceImp implements BookService {

    private final BookDAO bookDAO;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    @Inject
    public BookServiceImp(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        logger.info("BookServiceImp created");
    }

    public List<Book> getBooks() throws SQLException, ExecutionException, InterruptedException {
        return bookDAO.getBooks();
    }

    public boolean insertBook(Book book) throws ExecutionException, InterruptedException {
        return bookDAO.insertBook(book);
    }
}
