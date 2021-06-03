package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Photos {
    private final IntegerProperty photo_id;
    private final StringProperty path;
    private final IntegerProperty apartment_id;

    public Photos(IntegerProperty photo_id, StringProperty path, IntegerProperty apartment_id) {
        this.photo_id = photo_id;
        this.path = path;
        this.apartment_id = apartment_id;
    }

    public int getPhoto_id() {
        return photo_id.get();
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id.set(photo_id);
    }

    public IntegerProperty photo_idProperty() {
        return photo_id;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public StringProperty pathProperty() {
        return path;
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
}
