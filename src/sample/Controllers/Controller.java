package sample.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Alerts;
import sample.DbHandler;
import sample.Dialogs.AddingNewClientDialog;
import sample.Models.Apartments;
import sample.Models.Booking;
import sample.Models.Client;
import sample.Models.Living;
import sample.RequestsSQL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Controller {
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
    DbHandler dH = DbHandler.getDbHandler();

    @FXML
    void initialize(){
        clientIdColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        clientPassportSeriesColumn.setCellValueFactory(cellData -> cellData.getValue().passport_seriesProperty().asObject());
        clientPassportNumberColumn.setCellValueFactory(cellData -> cellData.getValue().passport_numberProperty().asObject());
        clientNameColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().nameProperty());
        clientSurnameColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().surnameProperty());
        clientPatronymicColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().patronymicProperty());
        clientBirthdayColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().birthdayProperty());
        clientTelephoneColumn.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().telephoneProperty());

        livingIdColumn.setCellValueFactory(cellData -> cellData.getValue().living_idProperty().asObject());
        livingSettlingColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().settlingProperty());
        livingEvictionColumn.setCellValueFactory(livingStringCellDataFeatures -> livingStringCellDataFeatures.getValue().evictionProperty());
        livingGuestsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_guestsProperty().asObject());
        livingKidsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_kidsProperty().asObject());
        livingApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        livingClientIdColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());
        livingASIdColumn.setCellValueFactory((cellData -> cellData.getValue().as_idProperty().asObject()));

        bookingIdColumn.setCellValueFactory(cellData -> cellData.getValue().booking_idProperty().asObject());
        bookingSettlingColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().settlingProperty());
        bookingEvictionColumn.setCellValueFactory(bookingStringCellDataFeatures -> bookingStringCellDataFeatures.getValue().evictionProperty());
        bookingGuestsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_guestsProperty().asObject());
        bookingKidsValueColumn.setCellValueFactory(cellData -> cellData.getValue().value_of_kidsProperty().asObject());
        bookingApartmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().apartment_idProperty().asObject());
        bookingClientIdColumn.setCellValueFactory(cellData -> cellData.getValue().client_idProperty().asObject());

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
        if(clientPassportSeriesTF.getText().equals("") || clientPassportNumberTF.getText().equals("") || //Если все поля основной формы заполнены, то передаём данные из полей на форму добавления
        clientNameTF.getText().equals("") || clientSurnameTF.getText().equals("") ||
        clientPatronymicTF.getText().equals("") || clientBirthdayDP.getValue() == null || clientTelephoneTF.getText().equals("")) {
            Alerts.showErrorAlert("Невозможная операция", "Регистрация клиента:", "Для регистрации клиента все поля обязательны к заполнению");
        }
        else {
            Client client = new Client();
            ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client);
            try {
                ancDialog.ShowDefaultDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Client client = new Client(0,
//                    Integer.parseInt(clientPassportSeriesTF.getText()),
//                    Integer.parseInt(clientPassportNumberTF.getText()),
//                    clientNameTF.getText(),
//                    clientSurnameColumn.getText(),
//                    clientPatronymicTF.getText(),
//                    clientBirthdayDP.getValue().toString(),
//                    clientTelephoneTF.getText()
//            );
//            ancDialog = new AddingNewClientDialog(false, "Views/AddingNewCustomer.fxml", "Регистрация клиента", client);
//            try {
//                ancDialog.ShowDefaultDialog();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
    public void OnMoveToLivings(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(livingsTab);
    }

    public void OnMoveToBookings(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(bookingsTab);
    }

    public void OnClickedClientsTableView(MouseEvent mouseEvent) {
        getClientEntryData();
    }

    public void getClientEntryData() {
        // check the table's selected item and get selected item
        if (clientsTableView.getSelectionModel().getSelectedItem() != null) {
            Client selectedClient = clientsTableView.getSelectionModel().getSelectedItem();
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
    public Tab livingsTab;
    public TextField livingsValueOfGuestsTF;
    public TextField livingsValueOfKidsTF;
    public TableView<Living> livingsTableView;
    public TableColumn<Living, Integer> livingIdColumn;
    public TableColumn<Living, String> livingSettlingColumn;
    public TableColumn<Living, String> livingEvictionColumn;
    public TableColumn<Living, Integer> livingGuestsValueColumn;
    public TableColumn<Living, Integer> livingKidsValueColumn;
    public TableColumn<Living, Integer> livingApartmentIdColumn;
    public TableColumn<Living, Integer> livingClientIdColumn;
    public TableColumn<Living, Integer> livingASIdColumn;
    private ObservableList<Living> livingsOList = FXCollections.observableArrayList();

    private void fillLivingsTableView(ResultSet localResultSet) throws SQLException {
        while (localResultSet.next())
        {
            Living living = new Living(
                new SimpleIntegerProperty(localResultSet.getInt(1)),
                new SimpleStringProperty(localResultSet.getString(2)),
                new SimpleStringProperty(localResultSet.getString(3)),
                new SimpleIntegerProperty(localResultSet.getInt(4)),
                new SimpleIntegerProperty(localResultSet.getInt(5)),
                new SimpleIntegerProperty(localResultSet.getInt(6)),
                new SimpleIntegerProperty(localResultSet.getInt(7)),
                new SimpleIntegerProperty(localResultSet.getInt(8))
            );
            livingsOList.add(living);
        }
        livingsTableView.setItems(livingsOList);
    }

    public void OnSelectAllLivings(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            fillClientsTableView(RequestsSQL.SelectAllFromLiving(connection));
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
            livingsValueOfGuestsTF.setText(String.valueOf(selectedLiving.getValue_of_guests()));
            livingsValueOfKidsTF.setText(String.valueOf(selectedLiving.getValue_of_kids()));
        }
    }

    public void OnShowAdditionalServices(ActionEvent actionEvent) {
    }

    public void OnMoveToClientByLiving(ActionEvent actionEvent) {
    }

    public void OnDeleteLivingData(ActionEvent actionEvent) {
    }

    public void OnChangeLivingData(ActionEvent actionEvent) {
    }



    //TAB BOOKINGS
    public Tab bookingsTab;
    public TextField bookingsValueOfGuestsTF;
    public TextField bookingsValueOfKidsTF;
    public CheckBox IsBookingsDataIncludedToNextClientRegistration;
    public TableView<Booking> bookingTableView;
    public TableColumn<Booking, Integer> bookingIdColumn;
    public TableColumn<Booking, String> bookingSettlingColumn;
    public TableColumn<Booking, String> bookingEvictionColumn;
    public TableColumn<Booking, Integer> bookingGuestsValueColumn;
    public TableColumn<Booking, Integer> bookingKidsValueColumn;
    public TableColumn<Booking, Integer> bookingApartmentIdColumn;
    public TableColumn<Booking, Integer> bookingClientIdColumn;
    private ObservableList<Booking> bookingsOList = FXCollections.observableArrayList();

    public void fillBookingsTableView(ResultSet localResultSet) throws SQLException {
        while (localResultSet.next())
        {
            Living living = new Living(
                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                    new SimpleStringProperty(localResultSet.getString(2)),
                    new SimpleStringProperty(localResultSet.getString(3)),
                    new SimpleIntegerProperty(localResultSet.getInt(4)),
                    new SimpleIntegerProperty(localResultSet.getInt(5)),
                    new SimpleIntegerProperty(localResultSet.getInt(6)),
                    new SimpleIntegerProperty(localResultSet.getInt(7)),
                    new SimpleIntegerProperty(localResultSet.getInt(8))
            );
            livingsOList.add(living);
        }
        livingsTableView.setItems(livingsOList);
    }

    public void OnSelectAllBookings(ActionEvent actionEvent) {
        try(Connection connection = dH.getConnection()) {
            fillClientsTableView(RequestsSQL.SelectAllFromBooking(connection));
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
            bookingsValueOfGuestsTF.setText(String.valueOf(selectedBooking.getValue_of_guests()));
            bookingsValueOfKidsTF.setText(String.valueOf(selectedBooking.getValue_of_kids()));
        }
    }

    public void OnMoveToClientByBooking(ActionEvent actionEvent) {
    }

    public void OnDeleteBookingData(ActionEvent actionEvent) {
    }
    public void OnChangeBookingData(ActionEvent actionEvent) {
    }

    //TAB APARTMENTS
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
    private ObservableList<Apartments> apartmentsOList = FXCollections.observableArrayList();

    public void fillApartmentsTableView(ResultSet localResultSet) throws SQLException {
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
            fillClientsTableView(RequestsSQL.SelectAllFromApartments(connection));
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
            apartmentsNumberTF.setText(String.valueOf(selectedApartments.getNumber()));
            apartmentsTypeCB.setValue(selectedApartments.getType());
            apartmentsPriceTF.setText(String.valueOf(selectedApartments.getType()));
            TextField discountNewValueTF;
        }
    }

    public void OnChangeDiscount(ActionEvent actionEvent) {
    }

    public void OnAddNewApartment(ActionEvent actionEvent) {
    }

    public void OnDeleteApartmentData(ActionEvent actionEvent) {
    }

    public void OnChangeApartmentData(ActionEvent actionEvent) {
    }

    public void OnViewPhotosOfApartment(ActionEvent actionEvent) {
    }
}
