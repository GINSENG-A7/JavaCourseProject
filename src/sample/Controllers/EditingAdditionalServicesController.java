package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Alerts;
import sample.DbHandler;
import sample.Models.AdditionalServices;
import sample.RequestsSQL;

import java.sql.Connection;
import java.sql.SQLException;

public class EditingAdditionalServicesController {
    public TextField asMinibarTF;
    public TextField asClothesWashingTF;
    public TextField asTelephoneTF;
    public TextField asIntercityTelephoneTF;
    public TextField asFoodTF;
    DbHandler dH = DbHandler.getDbHandler();
    private Stage dialogStage;
    private AdditionalServices relatedAS;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public AdditionalServices getRelatedAS() {
        return relatedAS;
    }

    public void setRelatedAS(AdditionalServices relatedAS) {
        this.relatedAS = relatedAS;
        asMinibarTF.setText(String.valueOf(relatedAS.getMini_bar()));
        asClothesWashingTF.setText(String.valueOf(relatedAS.getClothes_washing()));
        asTelephoneTF.setText(String.valueOf(relatedAS.getTelephone()));
        asIntercityTelephoneTF.setText(String.valueOf(relatedAS.getIntercity_telephone()));
        asFoodTF.setText(String.valueOf(relatedAS.getFood()));
    }

    public void onSaveChanges(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            if (
                    !asMinibarTF.getText().equals("") &&
                            !asClothesWashingTF.getText().equals("") &&
                            !asTelephoneTF.getText().equals("") &&
                            !asIntercityTelephoneTF.getText().equals("") &&
                            !asFoodTF.getText().equals("")
            ) {
                if (
                        Integer.parseInt(asMinibarTF.getText()) >= 0 &&
                                Integer.parseInt(asClothesWashingTF.getText()) >= 0 &&
                                Integer.parseInt(asTelephoneTF.getText()) >= 0 &&
                                Integer.parseInt(asIntercityTelephoneTF.getText()) >= 0 &&
                                Integer.parseInt(asFoodTF.getText()) >= 0
                ) {
                    RequestsSQL.changeAdditionalServices(
                            connection,
                            relatedAS.getAs_id(),
                            Integer.parseInt(asMinibarTF.getText()),
                            Integer.parseInt(asClothesWashingTF.getText()),
                            Integer.parseInt(asTelephoneTF.getText()),
                            Integer.parseInt(asIntercityTelephoneTF.getText()),
                            Integer.parseInt(asFoodTF.getText())
                    );
                    Alerts.showInformationAlert("", "Данные были успешно изменены.", "");
                } else {
                    Alerts.showWarningAlert("Недопустимые данные дополнительных услуг!", "Для применения изменений все значения должны быть больше нуля.", "");
                }
            } else {
                Alerts.showWarningAlert("Недопустимые данные дополнительных услуг!", "Для применения изменений все поля должны быть заполнены.", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onRefresh(ActionEvent actionEvent) {
        try (Connection connection = dH.getConnection()) {
            Integer[] tempASData = RequestsSQL.selectAllFromAdditionalServicesWhereIsSetLivingID(connection, relatedAS.getAs_id());
            if (tempASData.length > 0) {
                asMinibarTF.setText(String.valueOf(tempASData[1]));
                asClothesWashingTF.setText(String.valueOf(tempASData[2]));
                asTelephoneTF.setText(String.valueOf(tempASData[3]));
                asIntercityTelephoneTF.setText(String.valueOf(tempASData[4]));
                asFoodTF.setText(String.valueOf(tempASData[5]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
