package dao;


import models.Book;
import play.db.Database;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.inject.Inject;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAOImp implements BookDAO {

    @Inject
    private Database db;

    @Inject
    private JPAApi jpaApi;

    public BookDAOImp(){
        System.out.println(" BookDAOImp created");
    }

    public Book getBook() throws SQLException {

        System.out.println(" BookDAOImp getBook() " + db.getUrl());

        //JPA.em();
        //jpaApi.withTransaction();
        //Query q = jpaApi.em().createNativeQuery("select max(id) from Book");
        //BigInteger i = (BigInteger) q.getSingleResult();;
        //System.out.println("max is " + i.toString());

        BigInteger i2 = jpaApi.withTransaction(entityManager -> {
            Query query = entityManager.createNativeQuery("select max(id) from Book");
            return (BigInteger) query.getSingleResult();
        });

        System.out.println("max is " + i2.toString());

        Connection con = db.getConnection();
        //con.createStatement().execute( "insert into book values(null,'a','b')");

        PreparedStatement ps = con.prepareStatement("select * from Book b ");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println("iteration " + rs.getString("name"));

        }

        return new Book();
    }

}
