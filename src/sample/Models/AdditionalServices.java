package sample.Models;

import javafx.beans.property.IntegerProperty;

public class AdditionalServices {
    private IntegerProperty as_id;
    private IntegerProperty mini_bar;
    private IntegerProperty clothes_washing;
    private IntegerProperty telephone;
    private IntegerProperty intercity_telephone;
    private IntegerProperty food;

    public AdditionalServices(IntegerProperty as_id, IntegerProperty mini_bar, IntegerProperty clothes_washing, IntegerProperty telephone, IntegerProperty intercity_telephone, IntegerProperty food) {
        this.as_id = as_id;
        this.mini_bar = mini_bar;
        this.clothes_washing = clothes_washing;
        this.telephone = telephone;
        this.intercity_telephone = intercity_telephone;
        this.food = food;
    }

    public int getAs_id() {
        return as_id.get();
    }
    public IntegerProperty as_idProperty() {
        return as_id;
    }
    public void setAs_id(int as_id) {
        this.as_id.set(as_id);
    }

    public int getMini_bar() {
        return mini_bar.get();
    }
    public IntegerProperty mini_barProperty() {
        return mini_bar;
    }
    public void setMini_bar(int mini_bar) {
        this.mini_bar.set(mini_bar);
    }

    public int getClothes_washing() {
        return clothes_washing.get();
    }
    public IntegerProperty clothes_washingProperty() {
        return clothes_washing;
    }
    public void setClothes_washing(int clothes_washing) {
        this.clothes_washing.set(clothes_washing);
    }

    public int getTelephone() {
        return telephone.get();
    }
    public IntegerProperty telephoneProperty() {
        return telephone;
    }
    public void setTelephone(int telephone) {
        this.telephone.set(telephone);
    }

    public int getIntercity_telephone() {
        return intercity_telephone.get();
    }
    public IntegerProperty intercity_telephoneProperty() {
        return intercity_telephone;
    }
    public void setIntercity_telephone(int intercity_telephone) {
        this.intercity_telephone.set(intercity_telephone);
    }

    public int getFood() {
        return food.get();
    }
    public IntegerProperty foodProperty() {
        return food;
    }
    public void setFood(int food) {
        this.food.set(food);
    }
}
