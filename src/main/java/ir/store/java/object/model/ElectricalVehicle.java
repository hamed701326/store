package ir.store.java.object.model;

import ir.store.java.object.core.annotation.Entity;

@Entity
public class ElectricalVehicle extends Good {
    private String type;
    private int power;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Electrical Vehicle {" +
                "Id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type='" + this.type + '\'' +
                ", power=" + this.power +
                ", stock=" + getStock() +
                ", price=" + getPrice() +
                '}';
    }

}
