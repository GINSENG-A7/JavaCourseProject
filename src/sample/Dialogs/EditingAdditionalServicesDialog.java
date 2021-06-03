package sample.Dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.EditingAdditionalServicesController;
import sample.Main;
import sample.Models.AdditionalServices;

import java.io.IOException;

public class EditingAdditionalServicesDialog {
    private final boolean dialogResultIsOK;
    private final String viewFileName;
    private final String windowTitle;
    private final Integer livingId;
    private final AdditionalServices additionalServices;

    public EditingAdditionalServicesDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle, Integer livingId, AdditionalServices additionalServices) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
        this.livingId = livingId;
        this.additionalServices = additionalServices;
    }

    public boolean isDialogResultIsOK() {
        return dialogResultIsOK;
    }

    public void showDefaultDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(viewFileName));
        Parent page = loader.load(); // Тут вызывается initialize
        Stage editingAdditionalServicesStage = new Stage();
        editingAdditionalServicesStage.setTitle(windowTitle);
        editingAdditionalServicesStage.initModality(Modality.WINDOW_MODAL);
        editingAdditionalServicesStage.initOwner(Main.getMainStage());
        editingAdditionalServicesStage.setMinWidth(344);
        editingAdditionalServicesStage.setMinHeight(254);
        editingAdditionalServicesStage.setMaxWidth(344);
        editingAdditionalServicesStage.setMaxHeight(254);
        Scene scene = new Scene(page);
        editingAdditionalServicesStage.setScene(scene);
        EditingAdditionalServicesController controller = loader.getController();
        controller.setDialogStage(editingAdditionalServicesStage);
        if (this.additionalServices != null) {
            controller.setRelatedAS(additionalServices);
        }
        editingAdditionalServicesStage.showAndWait();
    }
}
