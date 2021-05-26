package sample.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private ObservableList<Client> clientsOList = FXCollections.observableArrayList();
    private Integer idOfSelectedClient = null;
    DbHandler dH = DbHandler.getDbHandler();

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

        try(Connection connection = dH.getConnection()) {
            apartmentsTypeCB.setItems(RequestsSQL.GetTypesOfApartments(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillClientsTableView(ResultSet localResultSet) throws SQLException {
        clientsOList.clear();
        while (localResultSet.next())
        {
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

    public void OnSelectAllClients(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            fillClientsTableView(RequestsSQL.SelectAllFromClient(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void OnRegisterNewClient(ActionEvent actionEvent) {
        if(clientPassportSeriesTF.getText().equals("") ||
                clientPassportNumberTF.getText().equals("") || //Если все поля основной формы заполнены, то передаём данные из полей на форму добавления
                clientNameTF.getText().equals("") ||
                clientSurnameTF.getText().equals("") ||
                clientPatronymicTF.getText().equals("") ||
                clientBirthdayDP.getValue() == null ||
                clientTelephoneTF.getText().equals("")
        ) {
            Client client = new Client();
            if(IsBookingsDataIncludedToNextClientRegistration.isSelected() == false) {
                ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client);
            }
            else {
                ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client, idOfSelectedBooking);
            }
            try {
                ancDialog.ShowDefaultDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            if(InputValidation.isRowConsistsOfNumbers(clientPassportSeriesTF.getText()) &&
                    InputValidation.CheckLenthOfPassportNumber(clientPassportNumberTF.getText()) &&
                    InputValidation.isRowConsistsOfNumbers(clientPassportNumberTF.getText()) &&
                    InputValidation.CheckLenthOfPassportSeries(clientPassportSeriesTF.getText()) &&
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
                if(IsBookingsDataIncludedToNextClientRegistration.isSelected() == false) {
                    ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client);
                }
                else {
                    ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client, idOfSelectedBooking);
                }
                try {
                    ancDialog.ShowDefaultDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Alerts.showWarningAlert("Неверный формат данных!", "Данные клиента имеют неверный формат", "");
            }
        }
    }

    public void OnMoveToLivings(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            if(idOfSelectedClient != null) {
                tabPane.getSelectionModel().select(livingsTab);
                fillLivingsTableView(RequestsSQL.SelectAllFromLivingByClientId(connection, idOfSelectedClient));
            }
            else {
                Alerts.showWarningAlert("Внимание!", "Клиент не выбран", "Необходимо выбрать клиента для отображения его проживаний");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OnMoveToBookings(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            if(idOfSelectedClient != null) {
                tabPane.getSelectionModel().select(bookingsTab);
                fillBookingsTableView(RequestsSQL.SelectAllFromBookingByClientId(connection, idOfSelectedClient));
            }
            else {
                Alerts.showWarningAlert("Внимание!", "Клиент не выбран", "Необходимо выбрать клиента для отображения его броней");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OnClickedClientsTableView(MouseEvent mouseEvent) {
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

    public void OnDeleteClientData(ActionEvent actionEvent) {
    }

    public void OnChangeClientData(ActionEvent actionEvent) {
    }

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
    private ObservableList<Living> livingsOList = FXCollections.observableArrayList();
    private Integer idOfSelectedLiving = null;
    private Integer idOfSelectedASFromLiving = null;
    private Integer idOfSelectedClientFromLiving = null;
    private Integer idOfAdditionalServices = null;

    private void fillLivingsTableView(ResultSet localResultSet) throws SQLException {
        livingsOList.clear();
        while (localResultSet.next())
        {
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

    public void OnSelectAllLivings(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            fillLivingsTableView(RequestsSQL.SelectAllFromLiving(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void OnClickedLivingsTableView(MouseEvent mouseEvent) {
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

    public void OnShowAdditionalServices(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            if(idOfSelectedLiving != null) {
                Integer[] idOfAdditionalServices = RequestsSQL.SelectAllFromAdditionalServicesWhereIsSetLivingID(connection, idOfSelectedASFromLiving);
                if(idOfAdditionalServices != null) {
                    AdditionalServices relatedAdditionalServices = new AdditionalServices(
                            new SimpleIntegerProperty(idOfAdditionalServices[0]),
                            new SimpleIntegerProperty(idOfAdditionalServices[1]),
                            new SimpleIntegerProperty(idOfAdditionalServices[2]),
                            new SimpleIntegerProperty(idOfAdditionalServices[3]),
                            new SimpleIntegerProperty(idOfAdditionalServices[4]),
                            new SimpleIntegerProperty(idOfAdditionalServices[5])
                    );
                    easDialog = new EditingAdditionalServicesDialog(false, "Views/EditingAdditionalServices.fxml", "Дополнительные услуги", idOfSelectedLiving, relatedAdditionalServices);
                    easDialog.ShowDefaultDialog();
                }
                else {
                    Alerts.showWarningAlert("", "У данного проживания нет информации о дополнительных услугах", "");
                }
            }
            else {
                Alerts.showWarningAlert("Ошибка открытия дополнительных услуг!", "Следует выбрать проживание для отображения информации о дополнительных услугах", "");
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    public void OnMoveToClientByLiving(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            if (idOfSelectedClientFromLiving != null){
                tabPane.getSelectionModel().select(clientsTab);
                fillClientsTableView(RequestsSQL.SelectAllFromClientByClientId(connection, idOfSelectedClientFromLiving));
            }
            else {
                Alerts.showWarningAlert("Внимание!", "Проживание не выбрано", "Необходимо выбрать проживание для отображения информации о клиенте");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OnDeleteLivingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите удалить данное проживание?", "");
        if(dialogResult == true) {
            if(idOfSelectedLiving != null && idOfSelectedASFromLiving != null) {
                try(Connection connection = dH.getConnection()) {
                    RequestsSQL.DeleteLivingEntry(connection, idOfSelectedLiving, idOfSelectedASFromLiving);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
            }
        }
    }

    public void OnChangeLivingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите изменить данные данного проживания?", "");
        if(dialogResult == true) {
            if(Integer.parseInt(livingsValueOfGuestsTF.getText()) > 0) {
                if(Integer.parseInt(livingsValueOfGuestsTF.getText()) < Integer.parseInt(livingsValueOfGuestsTF.getText())) {
                    try(Connection connection = dH.getConnection()) {
                        RequestsSQL.ChangeLivingEntry(
                                connection,
                                idOfSelectedLiving,
                                Integer.parseInt(livingsValueOfGuestsTF.getText()),
                                Integer.parseInt(livingsValueOfKidsTF.getText())
                        );
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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
    }



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
    private ObservableList<Booking> bookingsOList = FXCollections.observableArrayList();
    private Integer idOfSelectedBooking = null;
    private Integer idOfSelectedClientFromBooking = null;


    public void fillBookingsTableView(ResultSet localResultSet) throws SQLException {
        bookingsOList.clear();
        while (localResultSet.next())
        {
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

    public void OnSelectAllBookings(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            fillBookingsTableView(RequestsSQL.SelectAllFromBooking(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void OnClickedBookingsTableView(MouseEvent mouseEvent) {
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

    public void OnMoveToClientByBooking(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            if(idOfSelectedClientFromBooking != null) {
                tabPane.getSelectionModel().select(clientsTab);
                fillClientsTableView(RequestsSQL.SelectAllFromClientByClientId(connection, idOfSelectedClientFromBooking));
            }
            else {
                Alerts.showWarningAlert("Внимание!", "Бронь не выбрана", "Необходимо выбрать бронь для отображения информации о клиенте");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OnDeleteBookingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите удалить данную бронь?", "");
        if(dialogResult == true) {
            if(idOfSelectedLiving != null && idOfSelectedASFromLiving != null) {
                try(Connection connection = dH.getConnection()) {
                    RequestsSQL.DeleteBookingEntry(connection, idOfSelectedLiving);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                Alerts.showErrorAlert("", "Необходимо выбрать запись в таблице", "");
            }
        }
    }

    public void OnChangeBookingData(ActionEvent actionEvent) {
        Boolean dialogResult = Alerts.showConfirmationAlert("", "Вы уверены, что хотите изменить данные брони?", "");
        if(dialogResult == true) {
            if(Integer.parseInt(bookingsValueOfGuestsTF.getText()) > 0) {
                if(Integer.parseInt(bookingsValueOfGuestsTF.getText()) < Integer.parseInt(bookingsValueOfGuestsTF.getText())) {
                    try(Connection connection = dH.getConnection()) {
                        RequestsSQL.ChangeBookingEntry(
                                connection,
                                idOfSelectedBooking,
                                Integer.parseInt(bookingsValueOfGuestsTF.getText()),
                                Integer.parseInt(bookingsValueOfKidsTF.getText())
                        );
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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
    }

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
    private Integer idOfSelectedApartment;
    private ObservableList<Apartments> apartmentsOList = FXCollections.observableArrayList();

    public void fillApartmentsTableView(ResultSet localResultSet) throws SQLException {
        apartmentsOList.clear();
        while (localResultSet.next())
        {
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

    public void OnSelectAllApartments(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            fillApartmentsTableView(RequestsSQL.SelectAllFromApartments(connection));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void OnClickedApartmentsTableView(MouseEvent mouseEvent) {
        getApartmentsEntryData();
    }

    public void getApartmentsEntryData() {
        if (apartmentsTableView.getSelectionModel().getSelectedItem() != null) {
            Apartments selectedApartments = apartmentsTableView.getSelectionModel().getSelectedItem();
            idOfSelectedApartment = selectedApartments.getApartment_id();
            apartmentsNumberTF.setText(String.valueOf(selectedApartments.getNumber()));
            apartmentsTypeCB.setValue(selectedApartments.getType());
            apartmentsPriceTF.setText(String.valueOf(selectedApartments.getType()));
            TextField discountNewValueTF;
        }
    }

    public void OnChangeDiscount(ActionEvent actionEvent) {
    }

    public void OnAddNewApartment(ActionEvent actionEvent) throws IOException {
        annDialog = new AddingNewNumberDialog(false, "Views/AddingNewNumber.fxml", "Добавление нового номера", idOfSelectedApartment);
        annDialog.ShowDefaultDialog();
    }

    public void OnDeleteApartmentData(ActionEvent actionEvent) {
    }

    public void OnChangeApartmentData(ActionEvent actionEvent) {
    }

    public void OnViewPhotosOfApartment(ActionEvent actionEvent) throws IOException {
        if(idOfSelectedApartment != null) {
            vapDialog = new ViewingApartmentsPhotosDialog(false, "Views/ViewingApartmentsPhotos.fxml", "", idOfSelectedApartment);
            vapDialog.ShowDefaultDialog();
        }
        else {
            Alerts.showWarningAlert("Внимание!", "Номер не выбран", "Необходимо выбрать номер для отображения его фотографий");
        }
    }
}
