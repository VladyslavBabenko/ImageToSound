package com.github.vladyslavbabenko.imagetosound.javafx.controller;

import com.github.vladyslavbabenko.imagetosound.javafx.service.ImageToSoundService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("GUI.fxml")
public class FXController {
    private final ImageToSoundService imageToSoundService;
    private boolean isImageLoaded;
    private final String checkMark = "✔";
    private final Paint green = Color.GREEN;
    private final String xMark = "X";
    private final Paint red = Color.RED;

    @FXML
    private ImageView imageView;
    @FXML
    private Label imageStatusIndicator_1;
    @FXML
    private Label imageStatusIndicator_2;
    @FXML
    private Label audioStatusIndicator_1;
    @FXML
    private Button convertImageButton;

    @Autowired
    public FXController(ImageToSoundService imageToSoundService) {
        this.imageToSoundService = imageToSoundService;
    }

    /**
     * Sets selected image to the imageViewer and updates GUI
     */
    public void chooseImage() {
        imageView.setImage(imageToSoundService.convertToGrayScale(imageToSoundService.chooseImage("Select an image")));

        if (imageView.getImage() != null) {
            isImageLoaded = true;
            imageView.setOpacity(0.5);

            updateText(checkMark, green, xMark, red);

            convertImageButton.setDisable(!isImageLoaded);
        }
    }

    /**
     * Resets all values bringing the program to its starting point
     */
    public void reset() {
        imageToSoundService.reset();
        imageView.setImage(null);
        isImageLoaded = false;

        updateText(xMark, red, xMark, red);

        convertImageButton.setDisable(true);
    }

    /**
     * Updates status indicators
     *
     * @param imageIndicatorText  sets text for image status indicators
     * @param imageColorIndicator sets color for image status indicators
     * @param audioIndicatorText  sets text for audio status indicators
     * @param audioColorIndicator sets text for audio status indicators
     */
    private void updateText(String imageIndicatorText, Paint imageColorIndicator,
                            String audioIndicatorText, Paint audioColorIndicator) {
        imageStatusIndicator_1.setText(imageIndicatorText);
        imageStatusIndicator_1.setTextFill(imageColorIndicator);
        imageStatusIndicator_2.setText(imageIndicatorText);
        imageStatusIndicator_2.setTextFill(imageColorIndicator);
        audioStatusIndicator_1.setText(audioIndicatorText);
        audioStatusIndicator_1.setTextFill(audioColorIndicator);
    }

    /**
     * Calls a service method to save the converted image
     */
    public void saveImageAudio() {
        imageToSoundService.saveImageAudio();
    }
}