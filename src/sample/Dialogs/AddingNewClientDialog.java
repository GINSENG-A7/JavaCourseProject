package sample.Dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controllers.AddingNewClientController;
import sample.Main;
import sample.Models.Booking;
import sample.Models.Client;

import java.io.IOException;

public class AddingNewClientDialog {
    private boolean dialogResultIsOK;
    private String viewFileName;
    private String windowTitle;
    private Client client;
    private Integer bookingId;

    public boolean isDialogResultIsOK() {
        return dialogResultIsOK;
    }

    public void setDialogResultIsOK(boolean dialogResultIsOK) {
        this.dialogResultIsOK = dialogResultIsOK;
    }

    public AddingNewClientDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle, Client client) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
        this.client = client;
    }

    public AddingNewClientDialog(boolean dialogResultIsOK, String viewFileName, String windowTitle, Client client, Integer bookingId) {
        this.dialogResultIsOK = dialogResultIsOK;
        this.viewFileName = viewFileName;
        this.windowTitle = windowTitle;
        this.client = client;
        this.bookingId = bookingId;
    }

    public void ShowDefaultDialog() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(viewFileName));
        Parent page = loader.load(); // Тут вызывается initialize
        Stage addingNewClientStage = new Stage();
        addingNewClientStage.setTitle(windowTitle);
        addingNewClientStage.initModality(Modality.WINDOW_MODAL);
        addingNewClientStage.initOwner(Main.getMainStage());
        Scene scene = new Scene(page);
        addingNewClientStage.setScene(scene);
        AddingNewClientController controller = loader.getController();
        controller.setDialogStage(addingNewClientStage);
        if(this.bookingId == null) {
            controller.setAddableClient(this.client);
        }
        else {
            controller.setAddableClientWithBooking(this.client, this.bookingId);
        }
        addingNewClientStage.showAndWait();
    }
}
