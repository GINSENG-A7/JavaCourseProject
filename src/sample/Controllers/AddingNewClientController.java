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
        // Тут поверх всего проверить текстовые поля на ""
        if(!anCustomerPassportsSeriesTF.getText().equals("") &&
                !anCustomerPassportNumberTF.getText().equals("") &&
                !anCustomerNameTF.getText().equals("") &&
                !anCustomerSurnameTF.getText().equals("") &&
                !anCustomerPatronymicTF.getText().equals("") &&
                anCustomerBirthdayDP.getValue() != null &&
                !anCustomerTelephoneTF.getText().equals("")
        ) {
            if(InputValidation.isRowConsistsOfNumbers(anCustomerPassportsSeriesTF.getText()) &&
                    InputValidation.CheckLenthOfPassportNumber(anCustomerPassportNumberTF.getText()) &&
                    InputValidation.isRowConsistsOfNumbers(anCustomerPassportNumberTF.getText()) &&
                    InputValidation.CheckLenthOfPassportSeries(anCustomerPassportsSeriesTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(anCustomerNameTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(anCustomerSurnameTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(anCustomerPatronymicTF.getText()) &&
                    InputValidation.isRowConsistsOfNumbers(anCustomerTelephoneTF.getText()) &&
                    anCustomerBirthdayDP.getValue().isBefore(LocalDate.now())
            ) {
                if(InputValidation.isRowConsistsOfNumbers(anCustomerGuestsTF.getText()) &&
                        InputValidation.isRowConsistsOfNumbers(anCustomerKidsTF.getText()) &&
                        anCustomerSettlingDP.getValue().isAfter(LocalDate.now()) &&
                        anCustomerEvictionDP.getValue().isAfter(LocalDate.now())
                ) {
                    if(selectedApartment != null) {
                        // SQL-ВАЛИДАЦИЯ
                        try(Connection connection = dH.getConnection()) {
                            Boolean numberIsFreeForTheDate = RequestsSQL.IsNumberFreeForSetDate(
                                    connection,
                                    Date.valueOf(anCustomerSettlingDP.getValue()),
                                    Date.valueOf(anCustomerEvictionDP.getValue()),
                                    selectedApartment.getApartment_id()
                            );
                            if(numberIsFreeForTheDate == true) {
                                try {
                                    Integer clientIdByViewData = RequestsSQL.SelectNthIdFromClientWherePassportDataDefinedToString__ALTERNATIVE__(
                                            connection,
                                            Integer.parseInt(anCustomerPassportsSeriesTF.getText()),
                                            Integer.parseInt(anCustomerPassportNumberTF.getText())
                                    );
                                    if (clientIdByViewData != -1) {
                                        //Место для добавления нового клиета и проживания или бронирования к нему
                                        RequestsSQL.InsertBookingEntry(
                                                connection,
                                                clientIdByViewData,
                                                selectedApartment.getApartment_id(),
                                                Date.valueOf(anCustomerSettlingDP.getValue()),
                                                Date.valueOf(anCustomerEvictionDP.getValue()),
                                                Integer.parseInt(anCustomerGuestsTF.getText()),
                                                Integer.parseInt(anCustomerKidsTF.getText())

                                        );
                                    }
                                    else {
                                        Integer clientsActualBookingsAndLivings = RequestsSQL.ValueOfClientLivingsAndBookingsForToday(connection, addableСlient.getClient_id());
                                        if(clientsActualBookingsAndLivings <= 5) {
                                            RequestsSQL.InsertClientAndBookingAndEntry(
                                                    connection,
                                                    Integer.parseInt(anCustomerPassportsSeriesTF.getText()),
                                                    Integer.parseInt(anCustomerPassportNumberTF.getText()),
                                                    anCustomerNameTF.getText(),
                                                    anCustomerSurnameTF.getText(),
                                                    anCustomerPatronymicTF.getText(),
                                                    Date.valueOf(anCustomerBirthdayDP.getValue()),
                                                    anCustomerTelephoneTF.getText(),
                                                    selectedApartment.getApartment_id(),
                                                    Date.valueOf(anCustomerSettlingDP.getValue()),
                                                    Date.valueOf(anCustomerEvictionDP.getValue()),
                                                    Integer.parseInt(anCustomerGuestsTF.getText()),
                                                    Integer.parseInt(anCustomerKidsTF.getText())
                                            );
                                        }
                                    }
                                }
                                catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Alerts.showWarningAlert("Невозможно создать бронь!", "Номер уже занят на эту дату", "");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Alerts.showWarningAlert("Номер не был выбран!", "Для создания брони необходимо выбрать номер из таблицы", "");
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
        else {
            Alerts.showWarningAlert("Не все данные клинта были указаны!", "Все данные клиента обязательны к заполнению", "");
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
