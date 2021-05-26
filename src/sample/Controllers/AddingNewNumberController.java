package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddingNewNumberController {
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public TextField anNumberNumber;
    public ComboBox anNumberApartmentType;
    public TextField anNumberPrice;

    @FXML
    void initialize() {

    }

    public void OnChoosePhotos(ActionEvent actionEvent) {
    }

    public void OnRegisterNewNumber(ActionEvent actionEvent) {
    }
}
