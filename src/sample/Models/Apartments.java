package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Apartments {
    private final IntegerProperty apartment_id;
    private final IntegerProperty number;
    private final StringProperty type;
    private final IntegerProperty price;

    public Apartments(IntegerProperty apartment_id, IntegerProperty number, StringProperty type, IntegerProperty price) {
        this.apartment_id = apartment_id;
        this.number = number;
        this.type = type;
        this.price = price;
    }

    public int getApartment_id() {
        return apartment_id.get();
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id.set(apartment_id);
    }

    public IntegerProperty apartment_idProperty() {
        return apartment_id;
    }

    public int getNumber() {
        return number.get();
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public IntegerProperty priceProperty() {
        return price;
    }
}
