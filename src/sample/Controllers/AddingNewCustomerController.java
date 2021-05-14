package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Models.Apartments;

public class AddingNewCustomerController {
    public TableView<Apartments> anCustomerTableView; //Взять поля из модели Apartments вместо Client
    public TableColumn<Apartments, Integer> anCustomerApartmentIdColumn;
    public TableColumn<Apartments, Integer> anCustomerApartmentNumberColumn;
    public TableColumn<Apartments, String> anCustomerApartmentTypeColumn;
    public TableColumn<Apartments, Integer> anCustomerApartmentPriceColumn;
    public TextField anCustomerPassportsSeriesTF;
    public TextField anCustomerPassportNumberTF;
    public TextField anCustomerNameTF;
    public TextField anCustomerSurnameTF;
    public TextField anCustomerPatronymicTF;
    public TextField anCustomerTelephoneTF;
    public TextField anCustomerGuestsTF;
    public TextField anCustomerKidsTF;
    public DatePicker anCustomerBirthdayDP;
    public DatePicker anCustomerSettlingDP;
    public DatePicker anCustomerEvictionDP;
    public DatePicker anCustomerSettlingFilterDP;
    public DatePicker anCustomerEvictionFilterDP;
    public TextField anCustomerTopPriceFilterTF;
    public TextField anCustomerBottomPriceFilterTF;
    public ComboBox anCustomerApartmentTypeCB;
    public TextField anCustomerDiscountTF;
    public TextField anCustomerResultPriceTF;

    @FXML
    void initialize() {
        anCustomerApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        anCustomerApartmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        anCustomerApartmentTypeColumn.setCellValueFactory(apartmentsStringCellDataFeatures -> apartmentsStringCellDataFeatures.getValue().typeProperty());
        anCustomerApartmentPriceColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
    }
    public void OnRegisterNewLiving(ActionEvent actionEvent) {
    }

    public void OnCreateNewBooking(ActionEvent actionEvent) {
    }

    public void OnApplyFilters(ActionEvent actionEvent) {
    }
}
