package dao;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import models.Book;
import org.slf4j.LoggerFactory;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class BookDAOImp implements BookDAO {

    private final JPAApi jpaApi;

    final org.slf4j.Logger logger = LoggerFactory.getLogger(BookDAOImp.class);

    @Inject
    public BookDAOImp(JPAApi jpaApi) {
        logger.debug("BookDAOImp created");
        this.jpaApi = jpaApi;
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    public List<Book> getBooks() throws RuntimeException, ExecutionException, InterruptedException {
        //return supplyAsync(() -> wrap(em -> getBooks(em))).get();
        return supplyAsync(() -> wrap(this::getBooks)).get();
        //return jpaApi.em("default").createQuery("select b from Book b", Book.class).getResultList();
    }

    /**
     * Get books in the table Book
     * @return List with all the books in the table Book
     */
    public List<Book> getBooks(EntityManager em) {

        /*
        // example if we dont have @Transactional in the Controller

        Integer i2 = jpaApi.withTransaction(entityManager -> {
            Query query = entityManager.createNativeQuery("select max(id) from Book");
            return (Integer) query.getSingleResult();
        });
        */

        List<Book> books = em.createQuery("select b from Book b", Book.class).getResultList();

        return books;
    }


    public boolean insertBook(Book book) throws RuntimeException, InterruptedException, ExecutionException {
        return supplyAsync(() -> wrap(em -> insertBook(book, em))).get();
    }

    /**
     * Insert the book into the table Book
     */
    public boolean insertBook(Book book, EntityManager em) {

        try {
            em.persist(book);
            em.flush();
        }catch(Exception ew){
            return false;
        }

        return true;
    }

}
