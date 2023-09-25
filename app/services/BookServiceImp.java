package services;

import dao.BookDAO;
import models.Book;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookServiceImp implements BookService {

    @Inject
    BookDAO bookDAO;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    public BookServiceImp(){
        logger.info(" BookServiceImp created");
    }

    public List<Book> getBooks() throws SQLException, ExecutionException, InterruptedException {
        return bookDAO.getBooks();
    }

    public boolean insertBook(Book book) throws ExecutionException, InterruptedException {
        return bookDAO.insertBook(book);
    }
}
