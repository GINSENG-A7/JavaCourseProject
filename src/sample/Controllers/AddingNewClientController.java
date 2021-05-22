package sample.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.*;
import sample.Models.Apartments;
import sample.Models.Client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddingNewClientController {
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

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
    private Client addableСlient;
    private Apartments selectedApartment;
    private ObservableList<Apartments> anApartmentsOList = FXCollections.observableArrayList();
    DbHandler dH = DbHandler.getDbHandler();

    public Client getAddableСlient() {
        return addableСlient;
    }
    public void setAddableСlient(Client addableСlient) { //Тут просто неимоверный костыль
        this.addableСlient = addableСlient;
        anCustomerPassportsSeriesTF.setText(String.valueOf(addableСlient.getPassport_series()));
        anCustomerPassportNumberTF.setText(String.valueOf(addableСlient.getPassport_number()));
        anCustomerNameTF.setText(addableСlient.getName());
        anCustomerSurnameTF.setText(addableСlient.getSurname());
        anCustomerPatronymicTF.setText(addableСlient.getPatronymic());
        if(addableСlient.getBirthday() == null) {
            anCustomerBirthdayDP.setValue(null);
        }
        else {
            anCustomerBirthdayDP.setValue(LocalDate.parse(addableСlient.getBirthday()));
        }
        anCustomerTelephoneTF.setText(addableСlient.getTelephone());
    }

    @FXML
    void initialize() {
        anCustomerApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        anCustomerApartmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        anCustomerApartmentTypeColumn.setCellValueFactory(apartmentsStringCellDataFeatures -> apartmentsStringCellDataFeatures.getValue().typeProperty());
        anCustomerApartmentPriceColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        OnSelectAllApartments();

        try(Connection connection = dH.getConnection()) {
            anCustomerApartmentTypeCB.setItems(RequestsSQL.GetTypesOfApartments(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillApartmentsTableView(ResultSet localResultSet) throws SQLException {
        anApartmentsOList.clear();
        while (localResultSet.next())
        {
            Apartments apartments = new Apartments(
                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                    new SimpleStringProperty(localResultSet.getString(3)),
                    new SimpleIntegerProperty(localResultSet.getInt(4))
            );
            anApartmentsOList.add(apartments);
        }
        anCustomerTableView.setItems(anApartmentsOList);
    }

    public void OnSelectAllApartments() {
        try(Connection connection = dH.getConnection()) {
            fillApartmentsTableView(RequestsSQL.SelectAllFromApartments(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void OnRegisterNewLiving(ActionEvent actionEvent) {
    }

    public void OnCreateNewBooking(ActionEvent actionEvent) {
        if(InputValidation.isRowConsistsOfNumbers(anCustomerPassportsSeriesTF.getText()) &&
                InputValidation.CheckLenthOfPassportNumber(anCustomerPassportNumberTF.getText()) &&
                InputValidation.isRowConsistsOfNumbers(anCustomerPassportNumberTF.getText()) &&
                InputValidation.CheckLenthOfPassportSeries(anCustomerPassportsSeriesTF.getText()) &&
                InputValidation.isRowConsistsOfLetters(anCustomerNameTF.getText()) &&
                InputValidation.isRowConsistsOfLetters(anCustomerSurnameTF.getText()) &&
                InputValidation.isRowConsistsOfLetters(anCustomerPatronymicTF.getText()) &&
                InputValidation.isRowConsistsOfLetters(anCustomerTelephoneTF.getText()) &&
                anCustomerBirthdayDP.getValue().isBefore(LocalDate.now())
        ) {
            if(InputValidation.isRowConsistsOfLetters(anCustomerGuestsTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(anCustomerKidsTF.getText()) &&
                    anCustomerSettlingDP.getValue().isAfter(LocalDate.now()) &&
                    anCustomerBirthdayDP.getValue().isAfter(LocalDate.now())
            ) {
                if(true) { // ТУТ ДОПИСАТЬ SQL-ВАЛИДАЦИЮ
////////////////////////////////////////////////////////////////////////////////////////////
                }
            }
            else {
                Alerts.showWarningAlert("Неверный формат данных!", "Данные бронирования имеют неверный формат", "");
            }
        }
        else {
            Alerts.showWarningAlert("Неверный формат данных!", "Данные клиента имеют неверный формат", "");
        }
    }

    public void OnApplyFilters(ActionEvent actionEvent) {
        if(anCustomerApartmentTypeCB.getValue() != null) {
            try(Connection connection = dH.getConnection()) {
                fillApartmentsTableView(
                        RequestsSQL.ApplyTargetFilters(
                                connection,
                                anCustomerApartmentTypeCB.getValue().toString(),
                                Date.valueOf(anCustomerSettlingFilterDP.getValue()),
                                Date.valueOf(anCustomerEvictionFilterDP.getValue()),
                                Integer.parseInt(anCustomerBottomPriceFilterTF.getText()),
                                Integer.parseInt(anCustomerTopPriceFilterTF.getText())
                        )
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try(Connection connection = dH.getConnection()) {
                fillApartmentsTableView(
                        RequestsSQL.ApplyTargetFilters(
                                connection,
                                null,
                                Date.valueOf(anCustomerSettlingFilterDP.getValue()),
                                Date.valueOf(anCustomerEvictionFilterDP.getValue()),
                                Integer.parseInt(anCustomerBottomPriceFilterTF.getText()),
                                Integer.parseInt(anCustomerTopPriceFilterTF.getText())
                        )
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void OnClickedAddingNewClientTableView(MouseEvent mouseEvent) {
        getAddableApartmentEntryData();
    }

    public void getAddableApartmentEntryData() {
        // check the table's selected item and get selected item
        if (anCustomerTableView.getSelectionModel().getSelectedItem() != null) {
            selectedApartment = anCustomerTableView.getSelectionModel().getSelectedItem();
        }
    }
}
