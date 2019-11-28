package ir.store.java.object.model;

public class shoe extends Good {
    private String type;
    private int size;

    @Override
    public String toString() {
        return "Shoe {" +
                "Id=" + getId() +
                ", name='" + getName() + '\'' +
                " ,type='"+this.type+'\''+
                " ,size="+this.size+
                ", stock=" + getStock() +
                ", price=" + getPrice() +
                '}';
    }


}
