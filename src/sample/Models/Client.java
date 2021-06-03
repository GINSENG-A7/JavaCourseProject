package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private final IntegerProperty client_id;
    private final IntegerProperty passport_series;
    private final IntegerProperty passport_number;
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty patronymic;
    private final StringProperty birthday;
    private final StringProperty telephone;

    public Client(IntegerProperty client_id, IntegerProperty passport_series, IntegerProperty passport_number, StringProperty name, StringProperty surname, StringProperty patronymic, StringProperty birthday, StringProperty telephone) {
        this.client_id = client_id;
        this.passport_series = passport_series;
        this.passport_number = passport_number;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.telephone = telephone;
    }

    public Client(Integer client_id, Integer passport_series, Integer passport_number, String name, String surname, String patronymic, String birthday, String telephone) {
        this.client_id = new SimpleIntegerProperty(client_id);
        this.passport_series = new SimpleIntegerProperty(passport_series);
        this.passport_number = new SimpleIntegerProperty(passport_number);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.birthday = new SimpleStringProperty(birthday);
        this.telephone = new SimpleStringProperty(telephone);
    }

    public Client() {
        this(0, 0, 0, null, null, null, null, null);
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

    public int getPassport_series() {
        return passport_series.get();
    }

    public void setPassport_series(int passport_series) {
        this.passport_series.set(passport_series);
    }

    public IntegerProperty passport_seriesProperty() {
        return passport_series;
    }

    public int getPassport_number() {
        return passport_number.get();
    }

    public void setPassport_number(int passport_number) {
        this.passport_number.set(passport_number);
    }

    public IntegerProperty passport_numberProperty() {
        return passport_number;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public String getBirthday() {
        return birthday.get();
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }
}
