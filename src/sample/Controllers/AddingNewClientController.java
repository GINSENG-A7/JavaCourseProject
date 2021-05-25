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
import sample.Models.Booking;
import sample.Models.Client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

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
    private Client addableClient;
    private Apartments selectedApartment;
    private Integer relatedBookingId;
    private ObservableList<Apartments> anApartmentsOList = FXCollections.observableArrayList();
    DbHandler dH = DbHandler.getDbHandler();

    public Client getAddableClient() {
        return addableClient;
    }
    public void setAddableClient(Client addableClient) { //Тут просто неимоверный костыль
        this.addableClient = addableClient;
        anCustomerPassportsSeriesTF.setText(String.valueOf(addableClient.getPassport_series()));
        anCustomerPassportNumberTF.setText(String.valueOf(addableClient.getPassport_number()));
        anCustomerNameTF.setText(addableClient.getName());
        anCustomerSurnameTF.setText(addableClient.getSurname());
        anCustomerPatronymicTF.setText(addableClient.getPatronymic());
        if(addableClient.getBirthday() == null) {
            anCustomerBirthdayDP.setValue(null);
        }
        else {
            anCustomerBirthdayDP.setValue(LocalDate.parse(addableClient.getBirthday()));
        }
        anCustomerTelephoneTF.setText(addableClient.getTelephone());
    }
    public void setAddableClientWithBooking(Client addableClient, Integer relatedBookingId) { //Тут тоже, даже похуже
        this.addableClient = addableClient;
        this.relatedBookingId = relatedBookingId;
        anCustomerPassportsSeriesTF.setText(String.valueOf(addableClient.getPassport_series()));
        anCustomerPassportNumberTF.setText(String.valueOf(addableClient.getPassport_number()));
        anCustomerNameTF.setText(addableClient.getName());
        anCustomerSurnameTF.setText(addableClient.getSurname());
        anCustomerPatronymicTF.setText(addableClient.getPatronymic());
        if(addableClient.getBirthday() == null) {
            anCustomerBirthdayDP.setValue(null);
        }
        else {
            anCustomerBirthdayDP.setValue(LocalDate.parse(addableClient.getBirthday()));
        }
        anCustomerTelephoneTF.setText(addableClient.getTelephone());
//        SelectRelatedDataFromBookingById
        try(Connection connection = dH.getConnection()) {
            ResultSet rs1 = RequestsSQL.SelectRelatedDataFromBookingById(connection, relatedBookingId);
            if (rs1.next()) {
                anCustomerSettlingDP.setValue(LocalDate.parse(rs1.getString(1)));
                anCustomerEvictionDP.setValue(LocalDate.parse(rs1.getString(2)));
                anCustomerGuestsTF.setText(rs1.getString(3));
                anCustomerKidsTF.setText(rs1.getString(4));
                Integer relatedApartmentId = rs1.getInt(5);
                ResultSet rs2 = RequestsSQL.SelectAllFromApartmentsWhereApartmentIdIsSet(connection, relatedApartmentId);
                if(rs2.next()) {
                    Apartments pastingApartments = new Apartments(
                            new SimpleIntegerProperty(rs2.getInt(1)),
                            new SimpleIntegerProperty(rs2.getInt(2)),
                            new SimpleStringProperty(rs2.getString(3)),
                            new SimpleIntegerProperty(rs2.getInt(4))
                    );
                    anCustomerTableView.requestFocus();
                    anCustomerTableView.getSelectionModel().select(pastingApartments);
//                    anCustomerTableView.getFocusModel().focus(pastingApartments);
                }
                //Надо бы выбрать тут запись из таблицы, соответствующей переданому сюда с помощью запроса id номера
//                anCustomerTableView.setSelectionModel(TableView.TableViewSelectionModel<String>);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        anCustomerApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        anCustomerApartmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        anCustomerApartmentTypeColumn.setCellValueFactory(apartmentsStringCellDataFeatures -> apartmentsStringCellDataFeatures.getValue().typeProperty());
        anCustomerApartmentPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
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
                    if(Integer.parseInt(anCustomerGuestsTF.getText()) > 0) {
                        if(Integer.parseInt(anCustomerKidsTF.getText()) < Integer.parseInt(anCustomerGuestsTF.getText())) {
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
                                                RequestsSQL.InsertLivingEntry(
                                                        connection,
                                                        clientIdByViewData,
                                                        selectedApartment.getApartment_id(),
                                                        Date.valueOf(anCustomerSettlingDP.getValue()),
                                                        Date.valueOf(anCustomerEvictionDP.getValue()),
                                                        Integer.parseInt(anCustomerGuestsTF.getText()),
                                                        Integer.parseInt(anCustomerKidsTF.getText())
                                                );
                                                Alerts.showInformationAlert("Данные были успешно добавлены.","","");
                                            }
                                            else {
                                                Integer clientsActualBookingsAndLivings = RequestsSQL.ValueOfClientLivingsAndBookingsForToday(connection, addableClient.getClient_id());
                                                if(clientsActualBookingsAndLivings <= 5) {
                                                    RequestsSQL.InsertClientAndLivingAndAdditionalServicesEntry(
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
                                                    Alerts.showInformationAlert("Данные были успешно добавлены.","","");
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
                            Alerts.showWarningAlert("Недопустимые данные проживания!", "Количество детей не может быть больше количества взрослых", "");
                        }
                    }
                    else {
                        Alerts.showWarningAlert("Недопустимые данные проживания!", "Количество гостей должно быть больше нуля", "");
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
                    if(Integer.parseInt(anCustomerGuestsTF.getText()) > 0) {
                        if(Integer.parseInt(anCustomerKidsTF.getText()) < Integer.parseInt(anCustomerGuestsTF.getText())) {
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
                                                Integer clientsActualBookingsAndLivings = RequestsSQL.ValueOfClientLivingsAndBookingsForToday(connection, addableClient.getClient_id());
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
                            Alerts.showWarningAlert("Недопустимые данные брони!", "Количество детей не может быть больше количества взрослых", "");
                        }
                    }
                    else {
                        Alerts.showWarningAlert("Недопустимые данные брони!", "Количество гостей должно быть больше нуля", "");
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
        countFinalPrice();
    }

    public void getAddableApartmentEntryData() {
        // check the table's selected item and get selected item
        if (anCustomerTableView.getSelectionModel().getSelectedItem() != null) {
            selectedApartment = anCustomerTableView.getSelectionModel().getSelectedItem();
        }
    }

    public void countFinalPrice() {
        if(!anCustomerGuestsTF.getText().equals("") && !anCustomerKidsTF.getText().equals("") && anCustomerSettlingDP.getValue().isBefore(anCustomerEvictionDP.getValue())) {
            try (Connection connection = dH.getConnection()){
               Integer discount = RequestsSQL.GetCurrentDiscount(connection);
               Integer guestsCount = Integer.parseInt(anCustomerGuestsTF.getText());
               Integer kidsCount = Integer.parseInt(anCustomerKidsTF.getText());
               Period period = Period.between(anCustomerSettlingDP.getValue(), anCustomerEvictionDP.getValue());
               anCustomerDiscountTF.setText(String.valueOf(discount));
               anCustomerResultPriceTF.setText(String.valueOf(period.getDays() * selectedApartment.getPrice() * guestsCount + (period.getDays() * kidsCount * (selectedApartment.getPrice() - (selectedApartment.getPrice() / 100 * discount)))));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            anCustomerResultPriceTF.setText("<некорректные данные>");
        }
    }
}
