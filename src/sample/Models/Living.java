package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Living {
    private final IntegerProperty living_id;
    private final IntegerProperty client_id;
    private final StringProperty living_client_name; //
    private final StringProperty living_client_surname; //
    private final StringProperty living_client_patronymic; //
    private final StringProperty settling;
    private final StringProperty eviction;
    private final IntegerProperty living_apartment_number; //
    private final IntegerProperty value_of_guests;
    private final IntegerProperty value_of_kids;
    private final IntegerProperty apartment_id;
    private final IntegerProperty as_id;

    public Living(IntegerProperty living_id, IntegerProperty client_id, StringProperty living_client_name, StringProperty living_client_surname, StringProperty living_client_patronymic, StringProperty settling, StringProperty eviction, IntegerProperty living_apartment_number, IntegerProperty value_of_guests, IntegerProperty value_of_kids, IntegerProperty apartment_id, IntegerProperty as_id) {
        this.living_id = living_id;
        this.client_id = client_id;
        this.living_client_name = living_client_name;
        this.living_client_surname = living_client_surname;
        this.living_client_patronymic = living_client_patronymic;
        this.settling = settling;
        this.eviction = eviction;
        this.living_apartment_number = living_apartment_number;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
        this.as_id = as_id;
    }

    public int getLiving_id() {
        return living_id.get();
    }

    public void setLiving_id(int living_id) {
        this.living_id.set(living_id);
    }

    public IntegerProperty living_idProperty() {
        return living_id;
    }

    public String getLiving_client_name() {
        return living_client_name.get();
    }

    public void setLiving_client_name(String living_client_name) {
        this.living_client_name.set(living_client_name);
    }

    public StringProperty living_client_nameProperty() {
        return living_client_name;
    }

    public String getLiving_client_surname() {
        return living_client_surname.get();
    }

    public void setLiving_client_surname(String living_client_surname) {
        this.living_client_surname.set(living_client_surname);
    }

    public StringProperty living_client_surnameProperty() {
        return living_client_surname;
    }

    public String getLiving_client_patronymic() {
        return living_client_patronymic.get();
    }

    public void setLiving_client_patronymic(String living_client_patronymic) {
        this.living_client_patronymic.set(living_client_patronymic);
    }

    public StringProperty living_client_patronymicProperty() {
        return living_client_patronymic;
    }

    public String getSettling() {
        return settling.get();
    }

    public void setSettling(String settling) {
        this.settling.set(settling);
    }

    public StringProperty settlingProperty() {
        return settling;
    }

    public String getEviction() {
        return eviction.get();
    }

    public void setEviction(String eviction) {
        this.eviction.set(eviction);
    }

    public StringProperty evictionProperty() {
        return eviction;
    }

    public int getLiving_apartment_number() {
        return living_apartment_number.get();
    }

    public void setLiving_apartment_number(int living_apartment_number) {
        this.living_apartment_number.set(living_apartment_number);
    }

    public IntegerProperty living_apartment_numberProperty() {
        return living_apartment_number;
    }

    public int getValue_of_guests() {
        return value_of_guests.get();
    }

    public void setValue_of_guests(int value_of_guests) {
        this.value_of_guests.set(value_of_guests);
    }

    public IntegerProperty value_of_guestsProperty() {
        return value_of_guests;
    }

    public int getValue_of_kids() {
        return value_of_kids.get();
    }

    public void setValue_of_kids(int value_of_kids) {
        this.value_of_kids.set(value_of_kids);
    }

    public IntegerProperty value_of_kidsProperty() {
        return value_of_kids;
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

    public int getClient_id() {
        return client_id.get();
    }

    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }

    public IntegerProperty client_idProperty() {
        return client_id;
    }

    public int getAs_id() {
        return as_id.get();
    }

    public void setAs_id(int as_id) {
        this.as_id.set(as_id);
    }

    public IntegerProperty as_idProperty() {
        return as_id;
    }
}
