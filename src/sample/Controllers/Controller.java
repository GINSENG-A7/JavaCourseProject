package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Models.Apartments;
import sample.Models.Booking;
import sample.Models.Client;
import sample.Models.Living;

public class Controller {
    //TAB CLIENTS
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
    }
    public void OnSelectAllClients(ActionEvent actionEvent) {
    }
    public void OnRegisterNewClient(ActionEvent actionEvent) {
    }
    public void OnMoveToLivings(ActionEvent actionEvent) {
    }

    public void OnMoveToBookings(ActionEvent actionEvent) {
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

    public void OnSelectAllLivings(ActionEvent actionEvent) {
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

    public void OnSelectAllBookings(ActionEvent actionEvent) {
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

    public void OnSelectAllApartments(ActionEvent actionEvent) {
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
