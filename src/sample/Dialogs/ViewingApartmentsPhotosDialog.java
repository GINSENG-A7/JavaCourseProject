package sample.Dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.ViewingApartmentsPhotosController;
import sample.Main;

import java.io.IOException;

public class ViewingApartmentsPhotosDialog {
    private final boolean dialogResultIsOK;
    private final String viewFileName;
    private final String windowTitle;
    private Integer apartmentId;

    public ViewingApartmentsPhotosDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle, Integer apartmentId) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
        this.apartmentId = apartmentId;
    }

    public ViewingApartmentsPhotosDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
    }

    public void showDefaultDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(viewFileName));
        Parent page = loader.load(); // Тут вызывается initialize
        Stage viewingApartmentsPhotosStage = new Stage();
        viewingApartmentsPhotosStage.setTitle(windowTitle);
        viewingApartmentsPhotosStage.initModality(Modality.WINDOW_MODAL);
        viewingApartmentsPhotosStage.initOwner(Main.getMainStage());
        viewingApartmentsPhotosStage.setMinWidth(833);
        viewingApartmentsPhotosStage.setMinHeight(527);
        viewingApartmentsPhotosStage.setMaxWidth(833);
        viewingApartmentsPhotosStage.setMaxHeight(527);
        Scene scene = new Scene(page);
        viewingApartmentsPhotosStage.setScene(scene);
        ViewingApartmentsPhotosController controller = loader.getController();
        controller.setDialogStage(viewingApartmentsPhotosStage);
        if (apartmentId != null) {
            controller.setIdOfSelectedApartment(apartmentId);
        }
        viewingApartmentsPhotosStage.showAndWait();
    }
}
