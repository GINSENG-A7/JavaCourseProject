package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private IntegerProperty client_id;
    private IntegerProperty passport_series;
    private IntegerProperty passport_number;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty patronymic;
    private StringProperty birthday;
    private StringProperty telephone;

    public int getClient_id() {
        return client_id.get();
    }
    public IntegerProperty client_idProperty() {
        return client_id;
    }
    public void setClient_id(int client_id) {
        this.client_id.set(client_id);
    }

    public int getPassport_series() {
        return passport_series.get();
    }
    public IntegerProperty passport_seriesProperty() {
        return passport_series;
    }
    public void setPassport_series(int passport_series) {
        this.passport_series.set(passport_series);
    }

    public int getPassport_number() {
        return passport_number.get();
    }
    public IntegerProperty passport_numberProperty() {
        return passport_number;
    }
    public void setPassport_number(int passport_number) {
        this.passport_number.set(passport_number);
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }
    public StringProperty surnameProperty() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }
    public StringProperty patronymicProperty() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getBirthday() {
        return birthday.get();
    }
    public StringProperty birthdayProperty() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getTelephone() {
        return telephone.get();
    }
    public StringProperty telephoneProperty() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

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
}
