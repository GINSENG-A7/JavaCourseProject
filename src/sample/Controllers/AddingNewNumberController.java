package sample.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.*;
import sample.Models.Apartments;
import sample.Models.Photos;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddingNewNumberController {
    public TextField anNumberNumber;
    public ComboBox anNumberApartmentType;
    public TextField anNumberPrice;
    DbHandler dH = DbHandler.getDbHandler();
    private Stage dialogStage;
    private List<Photos> listOfPhotos;
    private List<File> addableFiles;
    private Integer idOfAddableApartment;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    void initialize() {
        try (Connection connection = dH.getConnection()) {
            anNumberApartmentType.setItems(RequestsSQL.getTypesOfApartments(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onChoosePhotos(ActionEvent actionEvent) throws IOException {
        List<File> filesCheckList = ImagesHandler.openFileChooser(dialogStage);
        if (filesCheckList != null) {
            addableFiles = new ArrayList<File>(ImagesHandler.copyAbsoluteFilesToRelativeFiles(filesCheckList));
        }
    }

    public void onRegisterNewNumber(ActionEvent actionEvent) {
        if (!anNumberNumber.equals("") && anNumberApartmentType != null && !anNumberPrice.equals("")) {
            if (InputValidation.isRowConsistsOfNumbers(anNumberNumber.getText()) && InputValidation.isRowConsistsOfNumbers(anNumberPrice.getText())) {
                if (Integer.parseInt(anNumberNumber.getText()) > 0 && Integer.parseInt(anNumberPrice.getText()) > 0) {
                    // SQL-Validation
                    try (Connection connection = dH.getConnection()) {
                        if (RequestsSQL.isNumberOfApartmentsUnique(connection, Integer.parseInt(anNumberNumber.getText()))) {
                            RequestsSQL.insertApartmentsEntry(
                                    connection,
                                    Integer.parseInt(anNumberNumber.getText()),
                                    anNumberApartmentType.getValue().toString(),
                                    Integer.parseInt(anNumberPrice.getText())
                            );
                            ResultSet localResultSet = RequestsSQL.selectApartmentsIdWithNumber(connection, Integer.parseInt(anNumberNumber.getText()));
                            localResultSet.next();
                            Apartments addableApartment = new Apartments(
                                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                                    new SimpleStringProperty(localResultSet.getString(3)),
                                    new SimpleIntegerProperty(localResultSet.getInt(4))
                            );
                            ImagesHandler.copyAbsoluteFilesToRelativeFiles(addableFiles);
                            // Дописать привязку файлов к записи добавленого номера в бд
                            for (int i = 0; i < addableFiles.size(); i++) {
                                RequestsSQL.insertPhotoEntry(connection, ImagesHandler.escapeBackSlashes(addableFiles.get(i).getPath()), addableApartment.getApartment_id());
                            }
                            dialogStage.close();
                        } else {
                            Alerts.showWarningAlert("Недопустимые данные номера!", "Номер с таким численным обозначением уже существует", "");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
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
}
