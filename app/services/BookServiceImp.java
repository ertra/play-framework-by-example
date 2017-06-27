package services;

import dao.BookDAO;
import models.Book;

import javax.inject.Inject;
import java.sql.SQLException;

public class BookServiceImp implements BookService {

    @Inject
    BookDAO bookDAO;

    public BookServiceImp(){
        System.out.println(" BookServiceImp created");
    }

    public Book getBook() throws SQLException {
        System.out.println(" BookServiceImp getBook()");
        return bookDAO.getBook();
    }


}
