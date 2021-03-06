package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage mainStage;
    private static Stage addingNewClientStage;
    private static Stage addingNewNumberStage;
    private static Stage editingAdditionalServicesStage;
    private static Stage viewingApartmentsPhotosStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Stage getAddingNewClientStage() {
        return addingNewClientStage;
    }

    public static Stage getAddingNewNumberStage() {
        return addingNewNumberStage;
    }

    public static Stage getEditingAdditionalServicesStage() {
        return editingAdditionalServicesStage;
    }

    public static Stage getViewingApartmentsPhotosStage() {
        return viewingApartmentsPhotosStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Views/sample.fxml"));
            primaryStage.setTitle("АРМ администратора гостиницы");
            primaryStage.setScene(new Scene(root, 1048, 489));
            primaryStage.setMinWidth(1048);
            primaryStage.setMinHeight(489);
            primaryStage.show();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
