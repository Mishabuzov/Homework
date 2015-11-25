package entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private double weight;
    private String country;
    private Integer categoryId;

    public Product(){
    }

    public Product(int id, String name, double price, double weight, String country, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.country = country;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getCountry() {
        return country;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

}
