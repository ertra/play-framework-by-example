package services;

import models.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BookService {

    List<Book> getBooks() throws SQLException, ExecutionException, InterruptedException;
    boolean insertBook(Book book) throws ExecutionException, InterruptedException;

}
