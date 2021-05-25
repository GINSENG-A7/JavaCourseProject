package sample.Dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.AddingNewClientController;
import sample.Controllers.EditingAdditionalServicesController;
import sample.Main;
import sample.Models.AdditionalServices;
import sample.Models.Client;

import java.io.IOException;

public class EditingAdditionalServicesDialog {
    private boolean dialogResultIsOK;
    private String viewFileName;
    private String windowTitle;
    private Integer livingId;
    private AdditionalServices additionalServices;

    public boolean isDialogResultIsOK() {
        return dialogResultIsOK;
    }

    public EditingAdditionalServicesDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle, Integer livingId, AdditionalServices additionalServices) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
        this.livingId = livingId;
        this.additionalServices = additionalServices;
    }

    public void ShowDefaultDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(viewFileName));
        Parent page = loader.load(); // Тут вызывается initialize
        Stage editingAdditionalServicesStage = new Stage();
        editingAdditionalServicesStage.setTitle(windowTitle);
        editingAdditionalServicesStage.initModality(Modality.WINDOW_MODAL);
        editingAdditionalServicesStage.initOwner(Main.getMainStage());
        Scene scene = new Scene(page);
        editingAdditionalServicesStage.setScene(scene);
        EditingAdditionalServicesController controller = loader.getController();
        controller.setDialogStage(editingAdditionalServicesStage);
        if(this.additionalServices != null) {
            controller.setRelatedAS(additionalServices);
        }
//        if(this.bookingId == null) {
//            controller.setAddableClient(this.client);
//        }
//        else {
//            controller.setAddableClientWithBooking(this.client, this.bookingId);
//        }
        editingAdditionalServicesStage.showAndWait();
    }
}
