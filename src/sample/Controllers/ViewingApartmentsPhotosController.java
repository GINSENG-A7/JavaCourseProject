package sample.Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.DbHandler;
import sample.Models.Photos;
import sample.RequestsSQL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewingApartmentsPhotosController {
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public ImageView vaPhotosImageView;
    public Image displayedImage;
    private Integer idOfSelectedApartment;
    private List<Image> listOfImages;
    private List<Photos> listOfPhotos;
    DbHandler dH = DbHandler.getDbHandler();

    public Integer getIdOfSelectedApartment() {
        return idOfSelectedApartment;
    }
    public void setIdOfSelectedApartment(Integer idOfSelectedApartment) {
        this.idOfSelectedApartment = idOfSelectedApartment;

        try(Connection connection = dH.getConnection()) {
            listOfPhotos = new ArrayList<Photos>(RequestsSQL.CollectImagesByApartmentId(connection, idOfSelectedApartment));
            vaPhotosImageView.setImage(SetImageByRelativePath(listOfPhotos.get(0).getPath()));
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void initialize() {

//        File file = new File("./Photos/73553398.jpg");
//        try {
//            BufferedImage image = ImageIO.read(file);
//            displayedImage = SwingFXUtils.toFXImage(image, null );
//            vaPhotosImageView.setImage(displayedImage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public Image SetImageByRelativePath(String relativePath) throws IOException {
        File file = new File(relativePath);
        BufferedImage image = ImageIO.read(file);
        Image resultImage = SwingFXUtils.toFXImage(image, null );
        return resultImage;
    }

    public List<Image> OpenFileChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
        List<File> file = fileChooser.showOpenMultipleDialog(dialogStage);
        if (!file.isEmpty()) {
            List<Image> resultImages = null;
            for(int i = 0; i < file.size(); i++) {
                BufferedImage image = ImageIO.read(file.get(i));
                resultImages.add(SwingFXUtils.toFXImage(image, null ));
            }
            return resultImages;
        }
        return null;
    }

    public void OnPreviousPicture(ActionEvent actionEvent) {
    }

    public void OnDeletePicture(ActionEvent actionEvent) {
    }

    public void OnAddPicture(ActionEvent actionEvent) {
    }

    public void OnNextPicture(ActionEvent actionEvent) {
    }
}
