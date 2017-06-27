package models;

import play.data.validation.Constraints;
import scala.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * for database examples
 */
@Entity
public class Book implements Serializable{

    @Id
    private Integer id;
    private String author;
    @Constraints.Required
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
