package ir.store.java.object.model;

import ir.store.java.object.core.annotation.Entity;

@Entity
public class Good {
    private int Id;
    private String name;
    private int stock;
    private int price;


    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
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

    public void setPrice(int price) {
        this.price = price;
    }

}
