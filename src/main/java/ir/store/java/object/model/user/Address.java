package ir.store.java.object.model.user;

public class Address{
    private String state;
    private String city;
    private String street;
    private long zipCode;
    Address(){}
    Address(String state, String city, String street, long zipCode) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getZipCode() {
        return zipCode;
    }

    public void setZipCode(long zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "'" + state + '\'' +" - '"+
                 city + '\'' +" - '"+
                 street + '\'' +" - "+
                " zipCode:" + zipCode;
    }
}
