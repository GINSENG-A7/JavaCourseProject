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
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public TextField anNumberNumber;
    public ComboBox anNumberApartmentType;
    public TextField anNumberPrice;
    private List<Photos> listOfPhotos;
    private List<File> addableFiles;
    private Integer idOfAddableApartment;
    DbHandler dH = DbHandler.getDbHandler();

    @FXML
    void initialize() {
        try(Connection connection = dH.getConnection()) {
            anNumberApartmentType.setItems(RequestsSQL.GetTypesOfApartments(connection));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OnChoosePhotos(ActionEvent actionEvent) throws IOException {
        addableFiles = new ArrayList<File>(ImagesHandler.CopyAbsoluteFilesToRelativeFiles(ImagesHandler.OpenFileChooser(dialogStage)));
    }

    public void OnRegisterNewNumber(ActionEvent actionEvent) {
        if(!anNumberNumber.equals("") && anNumberApartmentType != null && !anNumberPrice.equals("")) {
            if(InputValidation.isRowConsistsOfNumbers(anNumberNumber.getText()) && InputValidation.isRowConsistsOfNumbers(anNumberPrice.getText())) {
                if(Integer.parseInt(anNumberNumber.getText()) > 0 && Integer.parseInt(anNumberPrice.getText()) > 0){
                    // SQL-ВАЛИДАЦИЯ
                    try(Connection connection = dH.getConnection()) {
                        if(RequestsSQL.IsNumberOfApartmentsUnique(connection, Integer.parseInt(anNumberNumber.getText()))){
                            RequestsSQL.InsertApartmentsEntry(
                                    connection,
                                    Integer.parseInt(anNumberNumber.getText()),
                                    anNumberApartmentType.getValue().toString(),
                                    Integer.parseInt(anNumberPrice.getText())
                            );
                            ResultSet localResultSet = RequestsSQL.SelectApartmentsIdWithNumber(connection, Integer.parseInt(anNumberNumber.getText()));
                            localResultSet.next();
                            Apartments addableApartment = new Apartments(
                                    new SimpleIntegerProperty(localResultSet.getInt(1)),
                                    new SimpleIntegerProperty(localResultSet.getInt(2)),
                                    new SimpleStringProperty(localResultSet.getString(3)),
                                    new SimpleIntegerProperty(localResultSet.getInt(4))
                            );
                            ImagesHandler.CopyAbsoluteFilesToRelativeFiles(addableFiles);
                            // Дописать привязку файлов к записи добавленого номера в бд
                            for(int i = 0; i < addableFiles.size(); i++) {
                                RequestsSQL.InsertPhotoEntry(connection, ImagesHandler.EscapeBackSlashes(addableFiles.get(i).getPath()), addableApartment.getApartment_id());
                            }
                            dialogStage.close();
                        }
                        else {
                            Alerts.showWarningAlert("Недопустимые данные номера!", "Номер с таким численным обозначением уже существует", "");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Alerts.showWarningAlert("Недопустимые данные номера!", "Численное обозначение номера и стоимость проживания должны быть больше нуля", "");
                }
            }
            else {
                Alerts.showWarningAlert("Неверный формат данных!", "Данные номера имеют неверный формат", "");
            }
        }
        else {
            Alerts.showWarningAlert("Не все данные номера были указаны!", "Все данные номера обязательны к заполнению", "");
        }
    }
}
