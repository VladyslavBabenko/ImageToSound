package com.github.vladyslavbabenko.imagetosound.javafx.controller;

import com.github.vladyslavbabenko.imagetosound.javafx.enums.ImageConversionQuality;
import com.github.vladyslavbabenko.imagetosound.javafx.service.ImageToSoundService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    private final String checkMark = "✔";
    private final Paint green = Color.GREEN;
    private final String xMark = "X";
    private final Paint red = Color.RED;
    private boolean isImageLoaded;
    private boolean isAudioLoaded;
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
    @FXML
    private Button mashupButton;
    @FXML
    private Slider trackLengthSlider;
    @FXML
    private Slider relativeBrightnessSlider;

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

            updateImageStatus(checkMark, green);
            if (isImageLoaded && isAudioLoaded) {
                mashupButton.setDisable(false);
            }
            convertImageButton.setDisable(!isImageLoaded);
        }
    }

    /**
     * Selects audioFile from computer and updates GUI
     */
    public void chooseAudio() {
        if (imageToSoundService.chooseAudio("Select audio").length > 0) {

            isAudioLoaded = true;
            updateAudioStatus(checkMark, green);
            if (isImageLoaded && isAudioLoaded) {
                mashupButton.setDisable(false);
            }
            convertImageButton.setDisable(!isAudioLoaded);
        }
    }

    /**
     * Resets all values bringing the program to its starting point
     */
    public void reset() {
        imageToSoundService.reset();
        imageView.setImage(null);
        isAudioLoaded = isImageLoaded = false;

        updateAudioStatus(xMark, red);
        updateImageStatus(xMark, red);

        convertImageButton.setDisable(!isImageLoaded);
        mashupButton.setDisable(!(isImageLoaded && isAudioLoaded));
        trackLengthSlider.setValue(trackLengthSlider.getMin());
        relativeBrightnessSlider.setValue(0.0);
    }

    /**
     * Updates audio status indicators
     *
     * @param audioIndicatorText  sets text for audio status indicators
     * @param audioColorIndicator sets color for audio status indicators
     */
    private void updateAudioStatus(String audioIndicatorText, Paint audioColorIndicator) {
        audioStatusIndicator_1.setText(audioIndicatorText);
        audioStatusIndicator_1.setTextFill(audioColorIndicator);
    }

    /**
     * Updates image status indicators
     *
     * @param imageIndicatorText  sets text for image status indicators
     * @param imageColorIndicator sets color for image status indicators
     */
    private void updateImageStatus(String imageIndicatorText, Paint imageColorIndicator) {
        imageStatusIndicator_1.setText(imageIndicatorText);
        imageStatusIndicator_1.setTextFill(imageColorIndicator);
        imageStatusIndicator_2.setText(imageIndicatorText);
        imageStatusIndicator_2.setTextFill(imageColorIndicator);
    }

    /**
     * Calls a service method to save the converted image
     */
    public void saveImageAudio() {
        if (isImageLoaded) {
            imageToSoundService.saveImageAudio(ImageConversionQuality.VERY_GOOD, (int) trackLengthSlider.getValue());
        }
    }

    /**
     * Calls a service method to save the mashup of converted image and audio track
     */
    public void saveMashup() {
        if (isImageLoaded && isAudioLoaded) {
            imageToSoundService.setBrightness(relativeBrightnessSlider.getValue());
            imageToSoundService.saveMashup(ImageConversionQuality.VERY_GOOD, (int) trackLengthSlider.getValue());
        }
    }
}