package ir.store.java.object.model;

public class Shoe extends Good {
    private String type;
    private int size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

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
