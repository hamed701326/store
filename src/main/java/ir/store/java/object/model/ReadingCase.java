package ir.store.java.object.model;

import ir.store.java.object.core.annotation.Entity;

@Entity
public class ReadingCase extends Good {
    private String publication;
    private String author;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Reading Case {" +
                "Id=" + getId() +
                ", name='" + getName() + '\'' +
                " ,type='"+this.type+"\'"+
                ",publication='"+this.publication+'\''+
                " ,author='"+this.author+'\''+
                ", stock=" + getStock() +
                ", price=" + getPrice() +
                '}';
    }
}
