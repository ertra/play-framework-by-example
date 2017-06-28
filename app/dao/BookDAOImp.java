package dao;


import models.Book;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class BookDAOImp implements BookDAO {

    @Inject
    private JPAApi jpaApi;

    public BookDAOImp(){
        System.out.println(" BookDAOImp created");
    }

    /**
     * Get books in the table Book
     * @return List with all the books in the table Book
     */
    public List<Book> getBooks() throws SQLException {

        /*
         // example if we dont have @Transactional in the Controller

        Integer i2 = jpaApi.withTransaction(entityManager -> {
            Query query = entityManager.createNativeQuery("select max(id) from Book");
            return (Integer) query.getSingleResult();
        });
        */

        EntityManager em = jpaApi.em();
        List<Book> books = em.createQuery("select b from Book b", Book.class).getResultList();

        return books;
    }

    /**
     * Insert the book into the table Book
     */
    public boolean insertBook(Book book) {

        EntityManager em = jpaApi.em();
        em.persist(book);

        return true;
    }

}
