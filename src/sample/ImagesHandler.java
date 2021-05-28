package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImagesHandler {
    public static Image SetImageByRelativePath(String relativePath) throws IOException {
        File file = new File(relativePath);
        BufferedImage image = ImageIO.read(file);
        Image resultImage = SwingFXUtils.toFXImage(image, null );
        return resultImage;
    }

    public static List<File> OpenFileChooser(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        return files;
    }

    public static List<File> CopyAbsoluteFilesToRelativeFiles(List<File> absoluteFiles) {
        List<File> relativeFiles = new ArrayList<File>();
        for(int i = 0; i < absoluteFiles.size(); i++) {
            try {
                Files.copy(absoluteFiles.get(i).toPath(), Path.of("./Photos/" + absoluteFiles.get(i).getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            relativeFiles.add(new File("./Photos/" + absoluteFiles.get(i).getName()));
        }
        return relativeFiles;
    }

    public static List<Image> ConvertFilesToImages (List<File> files) throws IOException {
        if (!files.isEmpty()) {
            List<Image> resultImages = new ArrayList<Image>();
            for(int i = 0; i < files.size(); i++) {
                BufferedImage image = ImageIO.read(files.get(i));
                resultImages.add(SwingFXUtils.toFXImage(image, null ));
            }
            return resultImages;
        }
        return null;
    }

    public static String EscapeBackSlashes(String inputStr) { //Можно поменять на StringBuilder
        char[]  inputStrAsChars = inputStr.toCharArray();
        String resultStr = "";
        for(int i = 0; i < inputStrAsChars.length; i++) {
            if(inputStrAsChars[i] == '\\') {
                resultStr = resultStr + '\\';
            }
            resultStr = resultStr + inputStrAsChars[i];
        }
        return resultStr;
    }
}
