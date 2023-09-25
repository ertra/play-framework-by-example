package dao;

import models.Book;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BookDAO {

    List<Book> getBooks() throws ExecutionException, InterruptedException;
    boolean insertBook(Book book) throws ExecutionException, InterruptedException;

}
