package services;

import models.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookService {

    List<Book> getBooks() throws SQLException;
    boolean insertBook(Book book);

}
