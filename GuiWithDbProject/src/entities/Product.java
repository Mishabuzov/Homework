package entities;

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

    public Product(String name, double price, double weight, String country, Integer categoryId) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.country = country;
        this.categoryId = categoryId;
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
