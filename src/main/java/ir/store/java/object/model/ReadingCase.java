package ir.store.java.object.model;

public class ReadingCase extends Good {
    private String publication;
    private String author;
    @Override
    public String toString() {
        return "Reading Case {" +
                "Id=" + getId() +
                ", name='" + getName() + '\'' +
                " ,type='"+this.publication+'\''+
                " ,author='"+this.author+'\''+
                ", stock=" + getStock() +
                ", price=" + getPrice() +
                '}';
    }
}
