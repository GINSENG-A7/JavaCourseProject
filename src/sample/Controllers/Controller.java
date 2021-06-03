package sample.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Alerts;
import sample.DbHandler;
import sample.Dialogs.AddingNewClientDialog;
import sample.Dialogs.AddingNewNumberDialog;
import sample.Dialogs.EditingAdditionalServicesDialog;
import sample.Dialogs.ViewingApartmentsPhotosDialog;
import sample.InputValidation;
import sample.Models.*;
import sample.RequestsSQL;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //TAB CLIENTS
    public AddingNewClientDialog ancDialog;
    public TabPane tabPane;
    public Tab clientsTab;
    public TextField clientPassportSeriesTF;
    public TextField clientPassportNumberTF;
    public TextField clientNameTF;
    public TextField clientSurnameTF;
    public TextField clientPatronymicTF;
    public DatePicker clientBirthdayDP;
    public TextField clientTelephoneTF;
    public TableView<Client> clientsTableView;
    public TableColumn<Client, Integer> clientIdColumn;
    public TableColumn<Client, Integer> clientPassportSeriesColumn;
    public TableColumn<Client, Integer> clientPassportNumberColumn;
    public TableColumn<Client, String> clientNameColumn;
    public TableColumn<Client, String> clientSurnameColumn;
    public TableColumn<Client, String> clientPatronymicColumn;
    public TableColumn<Client, String> clientBirthdayColumn;
    public TableColumn<Client, String> clientTelephoneColumn;
    //TAB LIVINGS
    public EditingAdditionalServicesDialog easDialog;
    public Tab livingsTab;
    public TextField livingsValueOfGuestsTF;
    public TextField livingsValueOfKidsTF;
    public TableView<Living> livingsTableView;
    public TableColumn<Living, Integer> livingIdColumn;
    public TableColumn<Living, Integer> livingClientIdColumn;
    public TableColumn<Living, String> livingClientNameColumn;
    public TableColumn<Living, String> livingClientSurnameColumn;
    public TableColumn<Living, String> livingClientPatronymicColumn;
    public TableColumn<Living, String> livingSettlingColumn;
    public TableColumn<Living, String> livingEvictionColumn;
    public TableColumn<Living, Integer> livingApartmentNumberColumn;
    public TableColumn<Living, Integer> livingGuestsValueColumn;
    public TableColumn<Living, Integer> livingKidsValueColumn;
    public TableColumn<Living, Integer> livingApartmentIdColumn;
    public TableColumn<Living, Integer> livingASIdColumn;
    //TAB BOOKINGS
    public Tab bookingsTab;
    public TextField bookingsValueOfGuestsTF;
    public TextField bookingsValueOfKidsTF;
    public CheckBox IsBookingsDataIncludedToNextClientRegistration;
    public TableView<Booking> bookingTableView;
    public TableColumn<Booking, Integer> bookingIdColumn;
    public TableColumn<Booking, Integer> bookingClientIdColumn;
    public TableColumn<Booking, String> bookingClientNameColumn;
    public TableColumn<Booking, String> bookingClientSurnameColumn;
    public TableColumn<Booking, String> bookingClientPatronymicColumn;
    public TableColumn<Booking, String> bookingSettlingColumn;
    public TableColumn<Booking, String> bookingEvictionColumn;
    public TableColumn<Booking, Integer> bookingApartmentNumberColumn;
    public TableColumn<Booking, Integer> bookingGuestsValueColumn;
    public TableColumn<Booking, Integer> bookingKidsValueColumn;
    public TableColumn<Booking, Integer> bookingApartmentIdColumn;
    //TAB APARTMENTS
    public ViewingApartmentsPhotosDialog vapDialog;
    public AddingNewNumberDialog annDialog;
    public Tab apartmentsTab;
    public TextField apartmentsNumberTF;
    public ComboBox apartmentsTypeCB;
    public TextField apartmentsPriceTF;
    public Label discountCurrentValueL;
    public TextField discountNewValueTF;
    public TableView<Apartments> apartmentsTableView;
    public TableColumn<Apartments, Integer> apartmentsIdColumn;
    public TableColumn<Apartments, Integer> apartmentsNumberColumn;
    public TableColumn<Apartments, String> apartmentsTypeColumn;
    public TableColumn<Apartments, Integer> apartmentsPriceColumn;
    DbHandler dH = DbHandler.getDbHandler();
    private final ObservableList<Client> clientsOList = FXCollections.observableArrayList();
    private Integer idOfSelectedClient = null;
    private final ObservableList<Living> livingsOList = FXCollections.observableArrayList();
    private Integer idOfSelectedLiving = null;
    private Integer idOfSelectedASFromLiving = null;
    private Integer idOfSelectedClientFromLiving = null;
    private final Integer idOfAdditionalServices = null;
    private final ObservableList<Booking> bookingsOList = FXCollections.observableArrayList();
    private Integer idOfSelectedBooking = null;
    private Integer idOfSelectedClientFromBooking = null;
    private Integer idOfSelectedApartment;
    private final ObservableList<Apartments> apartmentsOList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientIdColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        clientPassportSeriesColumn.setCellValueFactory(cellData -> cellData.getValue().passport_seriesProperty().asObject());
        clientPassportNumberColumn.setCellValueFactory(cellData -> cellData.getValue().passport_numberProperty().asObject());
        clientNameColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().nameProperty());
        clientSurnameColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().surnameProperty());
        clientPatronymicColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().patronymicProperty());
        clientBirthdayColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().birthdayProperty());
        clientTelephoneColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().telephoneProperty());

        livingIdColumn.setCellValueFactory(cellData -> cellData.getValue().living_idProperty().asObject());
        livingClientIdColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        livingClientNameColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().living_client_nameProperty());
        livingClientSurnameColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().living_client_surnameProperty());
        livingClientPatronymicColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().living_client_patronymicProperty());
        livingSettlingColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().settlingProperty());
        livingEvictionColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().evictionProperty());
        livingApartmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().living_apartment_numberProperty().asObject());
        livingGuestsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_guestsProperty().asObject());
        livingKidsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_kidsProperty().asObject());
        livingApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        livingASIdColumn.setCellValueFactory((cellData -> cellData.getValue().as_idProperty().asObject()));

        bookingIdColumn.setCellValueFactory(cellData -> cellData.getValue().booking_idProperty().asObject());
        bookingClientIdColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        bookingClientNameColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().booking_client_nameProperty());
        bookingClientSurnameColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().booking_client_surnameProperty());
        bookingClientPatronymicColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().booking_client_patronymicProperty());
        bookingSettlingColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().settlingProperty());
        bookingEvictionColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().evictionProperty());
        bookingApartmentNumberColumn.setCellValueFactory(cellData -> cellData.getValue().booking_apartment_numberProperty().asObject());
        bookingGuestsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_guestsProperty().asObject());
        bookingKidsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_kidsProperty().asObject());
        bookingApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());

        apartmentsIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        apartmentsNumberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        apartmentsTypeColumn.setCellValueFactory(apartmentsStringCellDataFeatures -> apartmentsStringCellDataFeatures.getValue().typeProperty());
        apartmentsPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        try (Connection connection = dH.getConnection()) {
            apartmentsTypeCB.setItems(RequestsSQL.getTypesOfApartments(connection));
            discountCurrentValueL.setText(RequestsSQL.getCurrentDiscount(connection) + "%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillClientsTableView(ResultSet localResultSet) throws SQLException {
        clientsOList.clear();
        while (localResultSet.next()) {
            Client client = new Client(
                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                    new SimpleIntegerProperty(localResultSet.getInt(3)),
                    new SimpleStringProperty(localResultSet.getString(4)),
                    new SimpleStringProperty(localResultSet.getString(5)),
                    new SimpleStringProperty(localResultSet.getString(6)),
                    new SimpleStringProperty(localResultSet.getString(7)),
                    new SimpleStringProperty(localResultSet.getString(8))
            );
            clientsOList.add(client);
        }
        clientsTableView.setItems(clientsOList);
    }

    public void onSelectAllClients(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            fillClientsTableView(RequestsSQL.selectAllFromClient(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onRegisterNewClient(ActionEvent actionEvent) {
        if (clientPassportSeriesTF.getText().equals("") ||
                clientPassportNumberTF.getText().equals("") || //Если все поля основной формы заполнены, то передаём данные из полей на форму добавления
                clientNameTF.getText().equals("") ||
                clientSurnameTF.getText().equals("") ||
                clientPatronymicTF.getText().equals("") ||
                clientBirthdayDP.getValue() == null ||
                clientTelephoneTF.getText().equals("")
        ) {
            Client client = new Client();
            if (IsBookingsDataIncludedToNextClientRegistration.isSelected() == false) {
                ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client);
            } else {
                ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client, idOfSelectedBooking);
            }
            try {
                ancDialog.showDefaultDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (InputValidation.isRowConsistsOfNumbers(clientPassportSeriesTF.getText()) &&
                    InputValidation.checkLenthOfPassportNumber(clientPassportNumberTF.getText()) &&
                    InputValidation.isRowConsistsOfNumbers(clientPassportNumberTF.getText()) &&
                    InputValidation.checkLenthOfPassportSeries(clientPassportSeriesTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(clientNameTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(clientSurnameTF.getText()) &&
                    InputValidation.isRowConsistsOfLetters(clientPatronymicTF.getText()) &&
                    InputValidation.isRowConsistsOfNumbers(clientTelephoneTF.getText()) &&
                    clientBirthdayDP.getValue().isBefore(LocalDate.now())
            ) {
                Client client = new Client(
                        -1,
                        Integer.parseInt(clientPassportSeriesTF.getText()),
                        Integer.parseInt(clientPassportNumberTF.getText()),
                        clientNameTF.getText(),
                        clientSurnameTF.getText(),
                        clientPatronymicTF.getText(),
                        clientBirthdayDP.getValue().toString(),
                        clientTelephoneTF.getText()
                );
                if (IsBookingsDataIncludedToNextClientRegistration.isSelected() == false) {
                    ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client);
                } else {
                    ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client, idOfSelectedBooking);
                }
                try {
                    ancDialog.showDefaultDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alerts.showWarningAlert("Неверный формат данных!", "Данные клиента имеют неверный формат", "");
            }
        }
    }

    public void onMoveToLivings(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (idOfSelectedClient != null) {
                tabPane.getSelectionModel().select(livingsTab);
                fillLivingsTableView(RequestsSQL.selectAllFromLivingByClientId(connection, idOfSelectedClient));
            } else {
                Alerts.showWarningAlert("Внимание!", "Клиент не выбран", "Необходимо выбрать клиента для отображения его проживаний");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onMoveToBookings(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (idOfSelectedClient != null) {
                tabPane.getSelectionModel().select(bookingsTab);
                fillBookingsTableView(RequestsSQL.selectAllFromBookingByClientId(connection, idOfSelectedClient));
            } else {
                Alerts.showWarningAlert("Внимание!", "Клиент не выбран", "Необходимо выбрать клиента для отображения его броней");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onClickedClientsTableView(MouseEvent mouseEvent) {
        getClientEntryData();
    }

    public void getClientEntryData() {
        // check the table's selected item and get selected item
        if (clientsTableView.getSelectionModel().getSelectedItem() != null) {
            Client selectedClient = clientsTableView.getSelectionModel().getSelectedItem();
            idOfSelectedClient = selectedClient.getClient_id();
            clientPassportSeriesTF.setText(String.valueOf(selectedClient.getPassport_series()));
            clientPassportNumberTF.setText(String.valueOf(selectedClient.getPassport_number()));
            clientNameTF.setText(selectedClient.getName());
            clientSurnameTF.setText(selectedClient.getSurname());
            clientPatronymicTF.setText(selectedClient.getPatronymic());
            clientBirthdayDP.setValue(LocalDate.parse(selectedClient.getBirthday()));
//            String sd = DateHandler.convertDateToString(selectedClient.getBirthday());
            clientTelephoneTF.setText(selectedClient.getTelephone());
        }
    }

    public void onDeleteClientData(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            Boolean hasClientNoLivingsOrBookings = RequestsSQL.doesClientHasNoLivingsAndBookings(connection, idOfSelectedClient);
            if (hasClientNoLivingsOrBookings == true) {
                Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите удалить данного клиента и все его данные?", "");
                if (dialogResult == true) {
                    if (idOfSelectedClient != null && clientsTableView.getSelectionModel() != null) {
                        RequestsSQL.deleteAllInfoAboutclient(connection, idOfSelectedClient);
                    } else {
                        Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
                    }
                }
            } else {
                Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите удалить данного клиента?", "");
                if (dialogResult == true) {
                    if (idOfSelectedClient != null && clientsTableView.getSelectionModel() != null) {
                        RequestsSQL.deleteNullableClient(connection, idOfSelectedClient);
                    } else {
                        Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onChangeClientData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите изменить данные данного клиента?", "");
        if (dialogResult == true) {
            if (
                    !clientPassportSeriesTF.getText().equals("") ||
                            !clientPassportNumberTF.getText().equals("") ||
                            !clientNameTF.getText().equals("") ||
                            !clientSurnameTF.getText().equals("") ||
                            !clientPatronymicTF.getText().equals("") ||
                            clientBirthdayDP.getValue() != null ||
                            !clientTelephoneTF.getText().equals("")
            ) {
                if (InputValidation.isRowConsistsOfNumbers(clientPassportSeriesTF.getText()) &&
                        InputValidation.checkLenthOfPassportNumber(clientPassportNumberTF.getText()) &&
                        InputValidation.isRowConsistsOfNumbers(clientPassportNumberTF.getText()) &&
                        InputValidation.checkLenthOfPassportSeries(clientPassportSeriesTF.getText()) &&
                        InputValidation.isRowConsistsOfLetters(clientNameTF.getText()) &&
                        InputValidation.isRowConsistsOfLetters(clientSurnameTF.getText()) &&
                        InputValidation.isRowConsistsOfLetters(clientPatronymicTF.getText()) &&
                        InputValidation.isRowConsistsOfNumbers(clientTelephoneTF.getText()) &&
                        clientBirthdayDP.getValue().isBefore(LocalDate.now())
                ) {
                    Client client = new Client(
                            -1,
                            Integer.parseInt(clientPassportSeriesTF.getText()),
                            Integer.parseInt(clientPassportNumberTF.getText()),
                            clientNameTF.getText(),
                            clientSurnameTF.getText(),
                            clientPatronymicTF.getText(),
                            clientBirthdayDP.getValue().toString(),
                            clientTelephoneTF.getText()
                    );
                    try (Connection connection = dH.getConnection()) {
                        RequestsSQL.updateClientWithValues(
                                connection,
                                idOfSelectedClient,
                                client.getPassport_series(),
                                client.getPassport_number(),
                                client.getName(),
                                client.getSurname(),
                                client.getPatronymic(),
                                Date.valueOf(client.getBirthday()),
                                client.getTelephone()

                        );
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    Alerts.showWarningAlert("Неверный формат данных!", "Данные клиента имеют неверный формат", "");
                }
            } else {
                Alerts.showWarningAlert("Недопустимые данные клиента!", "Для изменения данных клиента все поля обязательны к заполнению", "");
            }
        }
    }

    private void fillLivingsTableView(ResultSet localResultSet) throws SQLException {
        livingsOList.clear();
        while (localResultSet.next()) {
            Living living = new Living(
                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                    new SimpleStringProperty(localResultSet.getString(3)),
                    new SimpleStringProperty(localResultSet.getString(4)),
                    new SimpleStringProperty(localResultSet.getString(5)),
                    new SimpleStringProperty(localResultSet.getString(6)),
                    new SimpleStringProperty(localResultSet.getString(7)),
                    new SimpleIntegerProperty(localResultSet.getInt(8)),
                    new SimpleIntegerProperty(localResultSet.getInt(9)),
                    new SimpleIntegerProperty(localResultSet.getInt(10)),
                    new SimpleIntegerProperty(localResultSet.getInt(11)),
                    new SimpleIntegerProperty(localResultSet.getInt(12))
            );
            livingsOList.add(living);
        }
        livingsTableView.setItems(livingsOList);
    }

    public void onSelectAllLivings(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            fillLivingsTableView(RequestsSQL.selectAllFromLiving(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onClickedLivingsTableView(MouseEvent mouseEvent) {
        getLivingEntryData();
    }

    public void getLivingEntryData() {
        if (livingsTableView.getSelectionModel().getSelectedItem() != null) {
            Living selectedLiving = livingsTableView.getSelectionModel().getSelectedItem();
            idOfSelectedLiving = selectedLiving.getLiving_id();
            idOfSelectedClientFromLiving = selectedLiving.getClient_id();
            idOfSelectedASFromLiving = selectedLiving.getAs_id();
            livingsValueOfGuestsTF.setText(String.valueOf(selectedLiving.getValue_of_guests()));
            livingsValueOfKidsTF.setText(String.valueOf(selectedLiving.getValue_of_kids()));
        }
    }

    public void onShowAdditionalServices(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (idOfSelectedLiving != null) {
                Integer[] idOfAdditionalServices = RequestsSQL.selectAllFromAdditionalServicesWhereIsSetLivingID(connection, idOfSelectedASFromLiving);
                if (idOfAdditionalServices != null) {
                    AdditionalServices relatedAdditionalServices = new AdditionalServices(
                            new SimpleIntegerProperty(idOfAdditionalServices[0]),
                            new SimpleIntegerProperty(idOfAdditionalServices[1]),
                            new SimpleIntegerProperty(idOfAdditionalServices[2]),
                            new SimpleIntegerProperty(idOfAdditionalServices[3]),
                            new SimpleIntegerProperty(idOfAdditionalServices[4]),
                            new SimpleIntegerProperty(idOfAdditionalServices[5])
                    );
                    easDialog = new EditingAdditionalServicesDialog(false, "Views/EditingAdditionalServices.fxml", "Дополнительные услуги", idOfSelectedLiving, relatedAdditionalServices);
                    easDialog.showDefaultDialog();
                } else {
                    Alerts.showWarningAlert("", "У данного проживания нет информации о дополнительных услугах", "");
                }
            } else {
                Alerts.showWarningAlert("Ошибка открытия дополнительных услуг!", "Следует выбрать проживание для отображения информации о дополнительных услугах", "");
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onMoveToClientByLiving(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (idOfSelectedClientFromLiving != null) {
                tabPane.getSelectionModel().select(clientsTab);
                fillClientsTableView(RequestsSQL.selectAllFromClientByClientId(connection, idOfSelectedClientFromLiving));
            } else {
                Alerts.showWarningAlert("Внимание!", "Проживание не выбрано", "Необходимо выбрать проживание для отображения информации о клиенте");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteLivingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите удалить данное проживание?", "");
        if (dialogResult == true) {
            if (idOfSelectedLiving != null && idOfSelectedASFromLiving != null && livingsTableView.getSelectionModel() != null) {
                try (Connection connection = dH.getConnection()) {
                    RequestsSQL.deleteLivingEntry(connection, idOfSelectedLiving, idOfSelectedASFromLiving);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
            }
        }
    }

    public void onChangeLivingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите изменить данные данного проживания?", "");
        if (dialogResult == true) {
            if (idOfSelectedLiving != null && idOfSelectedASFromLiving != null && livingsTableView.getSelectionModel() != null) {
                if (Integer.parseInt(livingsValueOfGuestsTF.getText()) > 0) {
                    if (Integer.parseInt(livingsValueOfKidsTF.getText()) < Integer.parseInt(livingsValueOfGuestsTF.getText())) {
                        try (Connection connection = dH.getConnection()) {
                            RequestsSQL.changeLivingEntry(
                                    connection,
                                    idOfSelectedLiving,
                                    Integer.parseInt(livingsValueOfGuestsTF.getText()),
                                    Integer.parseInt(livingsValueOfKidsTF.getText())
                            );
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        Alerts.showWarningAlert("Недопустимые данные проживания!", "Количество детей не может быть больше количества взрослых", "");
                    }
                } else {
                    Alerts.showWarningAlert("Недопустимые данные проживания!", "Количество гостей должно быть больше нуля", "");
                }
            } else {
                Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
            }
        }
    }

    public void fillBookingsTableView(ResultSet localResultSet) throws SQLException {
        bookingsOList.clear();
        while (localResultSet.next()) {
            Booking booking = new Booking(
                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                    new SimpleStringProperty(localResultSet.getString(3)),
                    new SimpleStringProperty(localResultSet.getString(4)),
                    new SimpleStringProperty(localResultSet.getString(5)),
                    new SimpleStringProperty(localResultSet.getString(6)),
                    new SimpleStringProperty(localResultSet.getString(7)),
                    new SimpleIntegerProperty(localResultSet.getInt(8)),
                    new SimpleIntegerProperty(localResultSet.getInt(9)),
                    new SimpleIntegerProperty(localResultSet.getInt(10)),
                    new SimpleIntegerProperty(localResultSet.getInt(11))
            );
            bookingsOList.add(booking);
        }
        bookingTableView.setItems(bookingsOList);
    }

    public void onSelectAllBookings(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            fillBookingsTableView(RequestsSQL.selectAllFromBooking(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onClickedBookingsTableView(MouseEvent mouseEvent) {
        getBookingEntryData();
    }

    public void getBookingEntryData() {
        if (bookingTableView.getSelectionModel().getSelectedItem() != null) {
            Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
            idOfSelectedBooking = selectedBooking.getBooking_id();
            idOfSelectedClientFromBooking = selectedBooking.getClient_id();
            bookingsValueOfGuestsTF.setText(String.valueOf(selectedBooking.getValue_of_guests()));
            bookingsValueOfKidsTF.setText(String.valueOf(selectedBooking.getValue_of_kids()));
        }
    }

    public void onMoveToClientByBooking(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (idOfSelectedClientFromBooking != null) {
                tabPane.getSelectionModel().select(clientsTab);
                fillClientsTableView(RequestsSQL.selectAllFromClientByClientId(connection, idOfSelectedClientFromBooking));
            } else {
                Alerts.showWarningAlert("Внимание!", "Бронь не выбрана", "Необходимо выбрать бронь для отображения информации о клиенте");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteBookingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите удалить данную бронь?", "");
        if (dialogResult == true) {
            if (idOfSelectedLiving != null && idOfSelectedASFromLiving != null && bookingTableView.getSelectionModel() != null) {
                try (Connection connection = dH.getConnection()) {
                    RequestsSQL.deleteBookingEntry(connection, idOfSelectedLiving);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
            }
        }
    }

    public void onChangeBookingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите изменить данные брони?", "");
        if (dialogResult == true) {
            if (idOfSelectedLiving != null && idOfSelectedASFromLiving != null && bookingTableView.getSelectionModel() != null) {
                if (Integer.parseInt(bookingsValueOfGuestsTF.getText()) > 0) {
                    if (Integer.parseInt(bookingsValueOfKidsTF.getText()) < Integer.parseInt(bookingsValueOfGuestsTF.getText())) {
                        try (Connection connection = dH.getConnection()) {
                            RequestsSQL.changeBookingEntry(
                                    connection,
                                    idOfSelectedBooking,
                                    Integer.parseInt(bookingsValueOfGuestsTF.getText()),
                                    Integer.parseInt(bookingsValueOfKidsTF.getText())
                            );
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        Alerts.showWarningAlert("Недопустимые данные брони!", "Количество детей не может быть больше количества взрослых", "");
                    }
                } else {
                    Alerts.showWarningAlert("Недопустимые данные брони!", "Количество гостей должно быть больше нуля", "");
                }
            } else {
                Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
            }
        }
    }

    public void fillApartmentsTableView(ResultSet localResultSet) throws SQLException {
        apartmentsOList.clear();
        while (localResultSet.next()) {
            Apartments living = new Apartments(
                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                    new SimpleStringProperty(localResultSet.getString(3)),
                    new SimpleIntegerProperty(localResultSet.getInt(4))
            );
            apartmentsOList.add(living);
        }
        apartmentsTableView.setItems(apartmentsOList);
    }

    public void onSelectAllApartments(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            fillApartmentsTableView(RequestsSQL.selectAllFromApartments(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onClickedApartmentsTableView(MouseEvent mouseEvent) {
        getApartmentsEntryData();
    }

    public void getApartmentsEntryData() {
        if (apartmentsTableView.getSelectionModel().getSelectedItem() != null) {
            Apartments selectedApartments = apartmentsTableView.getSelectionModel().getSelectedItem();
            idOfSelectedApartment = selectedApartments.getApartment_id();
            apartmentsNumberTF.setText(String.valueOf(selectedApartments.getNumber()));
            apartmentsTypeCB.setValue(selectedApartments.getType());
            apartmentsPriceTF.setText(String.valueOf(selectedApartments.getPrice()));
            TextField discountNewValueTF;
        }
    }

    public void onChangeDiscount(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            RequestsSQL.setNewDiscount(connection, Integer.parseInt(discountNewValueTF.getText()));
            discountCurrentValueL.setText(RequestsSQL.getCurrentDiscount(connection) + "%");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onAddNewApartment(ActionEvent actionEvent) throws IOException {
        annDialog = new AddingNewNumberDialog(false, "Views/AddingNewNumber.fxml", "Добавление нового номера", idOfSelectedApartment);
        annDialog.showDefaultDialog();
    }

    public void onDeleteApartmentData(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (RequestsSQL.isThereNoBookingsAndLivingsOnApartment(connection, idOfSelectedApartment) == true) {
                RequestsSQL.deleteApartmentsEntry(
                        connection,
                        idOfSelectedApartment
                );
            } else {
                Alerts.showWarningAlert("Недопустимые данные номера!", "Номер с таким численным обозначением уже существует", "");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onChangeApartmentData(ActionEvent actionEvent) {
        if (!apartmentsNumberTF.equals("") && apartmentsTypeCB != null && !apartmentsPriceTF.equals("")) {
            if (InputValidation.isRowConsistsOfNumbers(apartmentsNumberTF.getText()) && InputValidation.isRowConsistsOfNumbers(apartmentsNumberTF.getText())) {
                if (Integer.parseInt(apartmentsNumberTF.getText()) > 0 && Integer.parseInt(apartmentsNumberTF.getText()) > 0) {
                    //SQL-ВАЛИДАЦИЯ
                    try (Connection connection = dH.getConnection()) {
                        if (RequestsSQL.isThereNoBookingsAndLivingsOnApartment(connection, idOfSelectedApartment) == true) {
                            RequestsSQL.changeApartmentsEntry(
                                    connection,
                                    idOfSelectedApartment,
                                    Integer.parseInt(apartmentsNumberTF.getText()),
                                    apartmentsTypeCB.getValue().toString(),
                                    Integer.parseInt(apartmentsPriceTF.getText())
                            );
                        } else {
                            Alerts.showWarningAlert("Недопустимые данные номера!", "Номер с таким численным обозначением уже существует", "");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    Alerts.showWarningAlert("Недопустимые данные номера!", "Численное обозначение номера и стоимость проживания должны быть больше нуля", "");
                }
            } else {
                Alerts.showWarningAlert("Неверный формат данных!", "Данные номера имеют неверный формат", "");
            }
        } else {
            Alerts.showWarningAlert("Не все данные номера были указаны!", "Все данные номера обязательны к заполнению", "");
        }
    }

    public void onViewPhotosOfApartment(ActionEvent actionEvent) throws IOException {
        if (idOfSelectedApartment != null) {
            vapDialog = new ViewingApartmentsPhotosDialog(false, "Views/ViewingApartmentsPhotos.fxml", "", idOfSelectedApartment);
            vapDialog.showDefaultDialog();
        } else {
            Alerts.showWarningAlert("Внимание!", "Номер не выбран", "Необходимо выбрать номер для отображения его фотографий");
        }
    }
}
