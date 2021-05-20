package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Booking {
    private IntegerProperty booking_id;
    private StringProperty settling;
    private StringProperty eviction;
    private IntegerProperty value_of_guests;
    private IntegerProperty value_of_kids;
    private IntegerProperty apartment_id;
    private IntegerProperty client_id;

    public Booking(IntegerProperty booking_id, StringProperty settling, StringProperty eviction, IntegerProperty value_of_guests, IntegerProperty value_of_kids, IntegerProperty apartment_id, IntegerProperty client_id) {
        this.booking_id = booking_id;
        this.settling = settling;
        this.eviction = eviction;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
        this.client_id = client_id;
    }

    public int getBooking_id() {
        return booking_id.get();
    }
    public IntegerProperty booking_idProperty() {
        return booking_id;
    }
    public void setBooking_id(int booking_id) {
        this.booking_id.set(booking_id);
    }

    public String getSettling() {
        return settling.get();
    }
    public StringProperty settlingProperty() {
        return settling;
    }
    public void setSettling(String settling) {
        this.settling.set(settling);
    }

    public String getEviction() {
        return eviction.get();
    }
    public StringProperty evictionProperty() {
        return eviction;
    }
    public void setEviction(String eviction) {
        this.eviction.set(eviction);
    }

    public int getValue_of_guests() {
        return value_of_guests.get();
    }
    public IntegerProperty value_of_guestsProperty() {
        return value_of_guests;
    }
    public void setValue_of_guests(int value_of_guests) {
        this.value_of_guests.set(value_of_guests);
    }

    public int getValue_of_kids() {
        return value_of_kids.get();
    }
    public IntegerProperty value_of_kidsProperty() {
        return value_of_kids;
    }
    public void setValue_of_kids(int value_of_kids) {
        this.value_of_kids.set(value_of_kids);
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

    public int getClient_id() {
        return client_id.get();
    }
    public IntegerProperty client_idProperty() {
        return client_id;
    }
    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }
}