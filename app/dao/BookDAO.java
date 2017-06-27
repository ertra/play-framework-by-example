package dao;


import models.Book;

import java.sql.SQLException;

public interface BookDAO {

    Book getBook() throws SQLException;

}
