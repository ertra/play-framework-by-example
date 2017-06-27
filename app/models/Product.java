package models;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String ean;
    private String author;
    private String name;
    private String description;

    public String getEan() {
        return ean;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Product() {

    }

    public Product(String ean, String author, String name, String description) {
        this.ean = ean;
        this.name = name;
        this.author = author;
        this.description = description;
    }


    public String toString() {
        return String.format("- " + ean + " - " + author + " , " + name);
    }


    // helper code to have ArrayList with Products
    private static List<Product> products;

    static {
        products = new ArrayList<Product>();
        products.add(new Product("111", "Jack", "Book about flowers",
                "description about book 111"));
        products.add(new Product("112", "Tom","Good read",
                "description about book 112"));
        products.add(new Product("113", "Peter","About cars",
                "description about book 113"));
        products.add(new Product("114", "Jenny","My journey",
                "description about book 114"));

    }

    public static List<Product> findAll() {
        return new ArrayList<Product>(products);
    }
}