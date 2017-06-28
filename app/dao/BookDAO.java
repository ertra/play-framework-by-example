package dao;


import models.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {

    List<Book> getBooks() throws SQLException;
    boolean insertBook(Book book);

}
