package sample.Dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.AddingNewNumberController;
import sample.Controllers.EditingAdditionalServicesController;
import sample.Main;
import sample.Models.Apartments;

import java.io.IOException;

public class AddingNewNumberDialog {
    private boolean dialogResultIsOK;
    private String viewFileName;
    private String windowTitle;
    private Integer apartmentId;

    public AddingNewNumberDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle, Integer apartmentId) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
        this.apartmentId = apartmentId;
    }

    public AddingNewNumberDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
    }

    public void ShowDefaultDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(viewFileName));
        Parent page = loader.load(); // Тут вызывается initialize
        Stage addingNewNumberStage = new Stage();
        addingNewNumberStage.setTitle(windowTitle);
        addingNewNumberStage.initModality(Modality.WINDOW_MODAL);
        addingNewNumberStage.initOwner(Main.getMainStage());
//        editingAdditionalServicesStage.setMinWidth(344);
//        editingAdditionalServicesStage.setMinHeight(254);
//        editingAdditionalServicesStage.setMaxWidth(344);
//        editingAdditionalServicesStage.setMaxHeight(254);
        Scene scene = new Scene(page);
        addingNewNumberStage.setScene(scene);
        AddingNewNumberController controller = loader.getController();
        controller.setDialogStage(addingNewNumberStage);
        if(this.apartmentId != null) {

        }
        addingNewNumberStage.showAndWait();
    }
}
