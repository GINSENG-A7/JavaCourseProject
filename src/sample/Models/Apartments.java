package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Apartments {
    private IntegerProperty apartment_id;
    private IntegerProperty number;
    private StringProperty type;
    private IntegerProperty price;

    public Apartments(IntegerProperty apartment_id, IntegerProperty number, StringProperty type, IntegerProperty price) {
        this.apartment_id = apartment_id;
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public int getApartment_id() {
        return apartment_id.get();
    }
    public IntegerProperty apartment_idProperty() {
        return apartment_id;
    }
    public void setApartment_id(int apartment_id) {
        this.apartment_id.set(apartment_id);
    }

    public int getNumber() {
        return number.get();
    }
    public IntegerProperty numberProperty() {
        return number;
    }
    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getType() {
        return type.get();
    }
    public StringProperty typeProperty() {
        return type;
    }
    public void setType(String type) {
        this.type.set(type);
    }

    public int getPrice() {
        return price.get();
    }
    public IntegerProperty priceProperty() {
        return price;
    }
    public void setPrice(int price) {
        this.price.set(price);
    }
}
