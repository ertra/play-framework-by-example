package services;

import dao.BookDAO;
import models.Book;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImp implements BookService {

    @Inject
    BookDAO bookDAO;

    public BookServiceImp(){
        System.out.println(" BookServiceImp created");
    }

    public List<Book> getBooks() throws SQLException {
        return bookDAO.getBooks();
    }

    public boolean insertBook(Book book) {
        return bookDAO.insertBook(book);
    }
}
