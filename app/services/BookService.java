package services;

import models.Book;

import java.sql.SQLException;

public interface BookService {

    Book getBook() throws SQLException;

}
