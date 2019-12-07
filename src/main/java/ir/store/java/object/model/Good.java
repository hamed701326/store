package ir.store.java.object.model;

import ir.store.java.object.core.annotation.Entity;

@Entity
public class Good {
    private int Id;
    private String name;
    private int stock;
    private Double price;
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(int id) {
        Id = id;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Good:" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price;
    }
}
