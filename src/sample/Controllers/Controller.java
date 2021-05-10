package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    public TableView clientsTableView;
    public TableColumn clientIdColumn;
    public TableColumn clientPassportSeriesColumn;
    public TableColumn clientPassportNumberColumn;
    public TableColumn clientNameColumn;
    public TableColumn clientSurnameColumn;
    public TableColumn clientPatronymicColumn;
    public TableColumn clientBirthdayColumn;
    public TableColumn clientTelephoneColumn;
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
    public TableView livingsTableView;
    public TableColumn livingIdColumn;
    public TableColumn livingSettlingColumn;
    public TableColumn livingEvictionColumn;
    public TableColumn livingGuestsValueColumn;
    public TableColumn livingKidsValueColumn;
    public TableColumn livingApartmentIdColumn;
    public TableColumn livingClientIdColumn;
    public TableColumn livingASIdColumn;

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
    public TableView bookingTableView;
    public TableColumn bookingIdColumn;
    public TableColumn bookingSettlingColumn;
    public TableColumn bookingEvictionColumn;
    public TableColumn bookingGuestsValueColumn;
    public TableColumn bookingKidsValueColumn;
    public TableColumn bookingApartmentIdColumn;
    public TableColumn bookingClientIdColumn;
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
    public TableView apartmentsTableView;
    public TableColumn apartmentsIdColumn;
    public TableColumn apartmentsNumberColumn;
    public TableColumn apartmentsTypeColumn;
    public TableColumn apartmentsPriceColumn;

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
    @FXML
    void initialize() {
    }
}
