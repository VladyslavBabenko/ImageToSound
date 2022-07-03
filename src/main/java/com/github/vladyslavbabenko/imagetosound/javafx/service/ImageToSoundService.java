package com.github.vladyslavbabenko.imagetosound.javafx.service;

import com.github.vladyslavbabenko.imagetosound.javafx.enums.ImageExtension;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ImageToSoundService {
    private Image image;
    private int width, height;

    /**
     * Creates a new window where user can select an image
     *
     * @return the selected image.
     */
    public Image chooseImage(String windowTitle) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(windowTitle);
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Extensions",
                Arrays.stream(ImageExtension.values())
                        .map(ImageExtension::getExtension)
                        .collect(Collectors.toList()));
        chooser.getExtensionFilters().add(imageFilter);
        String path = chooser.showOpenDialog(new Stage()).getAbsolutePath();
        image = new Image(path);
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        return image;
    }

}