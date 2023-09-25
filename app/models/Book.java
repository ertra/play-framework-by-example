package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import play.data.validation.Constraints;

import java.io.Serializable;

/**
 * for database examples
 */

@Entity
@Table(name="Book")
public class Book implements Serializable {

    @Id @GeneratedValue
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
