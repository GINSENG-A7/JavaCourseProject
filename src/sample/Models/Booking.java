package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Booking {
    private IntegerProperty booking_id;
    private IntegerProperty client_id;
    private StringProperty booking_client_name; //
    private StringProperty booking_client_surname; //
    private StringProperty booking_client_patronymic; //
    private StringProperty settling;
    private StringProperty eviction;
    private IntegerProperty booking_apartment_number; //
    private IntegerProperty value_of_guests;
    private IntegerProperty value_of_kids;
    private IntegerProperty apartment_id;

    public Booking(IntegerProperty booking_id, IntegerProperty client_id, StringProperty booking_client_name, StringProperty booking_client_surname, StringProperty booking_client_patronymic, StringProperty settling, StringProperty eviction, IntegerProperty booking_apartment_number, IntegerProperty value_of_guests, IntegerProperty value_of_kids, IntegerProperty apartment_id) {
        this.booking_id = booking_id;
        this.client_id = client_id;
        this.booking_client_name = booking_client_name;
        this.booking_client_surname = booking_client_surname;
        this.booking_client_patronymic = booking_client_patronymic;
        this.settling = settling;
        this.eviction = eviction;
        this.booking_apartment_number = booking_apartment_number;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
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

    public String getBooking_client_name() {
        return booking_client_name.get();
    }
    public StringProperty booking_client_nameProperty() {
        return booking_client_name;
    }
    public void setBooking_client_name(String booking_client_name) {
        this.booking_client_name.set(booking_client_name);
    }

    public String getBooking_client_surname() {
        return booking_client_surname.get();
    }
    public StringProperty booking_client_surnameProperty() {
        return booking_client_surname;
    }
    public void setBooking_client_surname(String booking_client_surname) {
        this.booking_client_surname.set(booking_client_surname);
    }

    public String getBooking_client_patronymic() {
        return booking_client_patronymic.get();
    }
    public StringProperty booking_client_patronymicProperty() {
        return booking_client_patronymic;
    }
    public void setBooking_client_patronymic(String booking_client_patronymic) {
        this.booking_client_patronymic.set(booking_client_patronymic);
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

    public int getBooking_apartment_number() {
        return booking_apartment_number.get();
    }
    public IntegerProperty booking_apartment_numberProperty() {
        return booking_apartment_number;
    }
    public void setBooking_apartment_number(int booking_apartment_number) {
        this.booking_apartment_number.set(booking_apartment_number);
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
