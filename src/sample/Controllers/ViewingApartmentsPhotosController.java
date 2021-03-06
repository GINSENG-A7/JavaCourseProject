package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Alerts;
import sample.DbHandler;
import sample.ImagesHandler;
import sample.Models.Photos;
import sample.RequestsSQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewingApartmentsPhotosController {
    public ImageView vaPhotosImageView;
    public Image displayedImage;
    DbHandler dH = DbHandler.getDbHandler();
    private Stage dialogStage;
    private Integer idOfSelectedApartment;
    //    private List<Image> listOfImages;
    private List<Photos> listOfPhotos;
    private Photos currentPhoto;
    private Integer indexOfCurrentImage = 0;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Integer getIdOfSelectedApartment() {
        return idOfSelectedApartment;
    }

    public void setIdOfSelectedApartment(Integer idOfSelectedApartment) {
        this.idOfSelectedApartment = idOfSelectedApartment;

        resetTableViewImages();
        centerImage();
    }

    @FXML
    void initialize() {
    }

    public void onAddPicture(ActionEvent actionEvent) throws IOException {
        List<File> filesCheckList = ImagesHandler.openFileChooser(dialogStage);
        if (filesCheckList != null) {
            List<File> addableFiles = new ArrayList<File>(ImagesHandler.copyAbsoluteFilesToRelativeFiles(filesCheckList));
            for (int i = 0; i < addableFiles.size(); i++) {
                try (Connection connection = dH.getConnection()) {
                    RequestsSQL.insertPhotoEntry(connection, ImagesHandler.escapeBackSlashes(addableFiles.get(i).getPath()), idOfSelectedApartment);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            resetTableViewImages();
        }
    }

    public void onDeletePicture(ActionEvent actionEvent) {
        if (listOfPhotos.size() > 0) {
            Boolean dialogResult = Alerts.showConfirmationAlert("????????????????", "?????????????????????? ?????????? ??????????????.", "");
            if (dialogResult == true) {
                try (Connection connection = dH.getConnection()) {
                    RequestsSQL.deletePhotoEntry(connection, listOfPhotos.get(indexOfCurrentImage).getPhoto_id());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                indexOfCurrentImage = 0;
                resetTableViewImages();
            }
        }
    }

    public void onPreviousPicture(ActionEvent actionEvent) throws IOException {
        if (listOfPhotos.size() > 0) {
            indexOfCurrentImage = indexOfCurrentImage - 1;
            if (indexOfCurrentImage >= 0) {
                vaPhotosImageView.setImage(ImagesHandler.setImageByRelativePath(listOfPhotos.get(indexOfCurrentImage).getPath()));
                currentPhoto = listOfPhotos.get(indexOfCurrentImage);
            } else {
                indexOfCurrentImage = listOfPhotos.size() - 1;
                vaPhotosImageView.setImage(ImagesHandler.setImageByRelativePath(listOfPhotos.get(indexOfCurrentImage).getPath()));
                currentPhoto = listOfPhotos.get(indexOfCurrentImage);
            }
            centerImage();
        }
    }

    public void onNextPicture(ActionEvent actionEvent) throws IOException {
        if (listOfPhotos.size() > 0) {
            indexOfCurrentImage = indexOfCurrentImage + 1;
            if (indexOfCurrentImage < listOfPhotos.size()) {
                vaPhotosImageView.setImage(ImagesHandler.setImageByRelativePath(listOfPhotos.get(indexOfCurrentImage).getPath()));
                currentPhoto = listOfPhotos.get(indexOfCurrentImage);
            } else {
                indexOfCurrentImage = 0;
                vaPhotosImageView.setImage(ImagesHandler.setImageByRelativePath(listOfPhotos.get(indexOfCurrentImage).getPath()));
                currentPhoto = listOfPhotos.get(indexOfCurrentImage);
            }
            centerImage();
        }
    }

    public void resetTableViewImages() {
        try (Connection connection = dH.getConnection()) {
            listOfPhotos = new ArrayList<Photos>(RequestsSQL.collectImagesByApartmentId(connection, idOfSelectedApartment));
            if (listOfPhotos.size() > 0) {
                vaPhotosImageView.setImage(ImagesHandler.setImageByRelativePath(listOfPhotos.get(indexOfCurrentImage).getPath()));
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    public void centerImage() {
        Image img = vaPhotosImageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = vaPhotosImageView.getFitWidth() / img.getWidth();
            double ratioY = vaPhotosImageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            vaPhotosImageView.setX((vaPhotosImageView.getFitWidth() - w) / 2);
            vaPhotosImageView.setY((vaPhotosImageView.getFitHeight() - h) / 2);

        }
    }
}
