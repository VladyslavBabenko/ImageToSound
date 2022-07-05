package com.github.vladyslavbabenko.imagetosound.javafx.service;

import com.github.vladyslavbabenko.imagetosound.javafx.enums.ImageConversionQuality;
import com.github.vladyslavbabenko.imagetosound.javafx.enums.ImageExtension;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

    /**
     * Converts input image to gray scale
     *
     * @param image input image
     * @return converted image to grayscale
     */
    public Image convertToGrayScale(Image image) {
        WritableImage result = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = result.getPixelWriter();

        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                pixelWriter.setColor(x, y, pixelReader.getColor(x, y).grayscale());
            }
        }

        return result;
    }

    /**
     * Converts input image to gray scale
     *
     * @param image   input image
     * @param quality must be a power of two (use ImageConversionQuality enum)
     * @return converted image to sine
     */
    private double[] convertImageToSine(Image image, int quality) {
        PixelReader pixelReader = image.getPixelReader();
        double[] imageSine = new double[width * quality];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double brightnessValue = pixelReader.getColor(x, (height - 1) - y).grayscale().getBrightness();
                for (int k = 0; k < quality; k++) {
                    imageSine[x * quality + k] += brightnessValue * Math.sin((k + y * 147.9) / 44100 * Math.PI * (500 + y * 150));
                }
            }
        }

        return imageSine;
    }

    /**
     * Calls all necessary methods to save the converted image
     */
    public void saveImageAudio(ImageConversionQuality quality, int trackLength) {
        saveAsWav(createAudio(quality, trackLength), getImageTitle());
    }

    /**
     * Gets the title of the image
     *
     * @return image title from url
     */
    private String getImageTitle() {
        String imageUrl = image.getUrl();
        return imageUrl.substring(imageUrl.lastIndexOf('\\') + 1, imageUrl.lastIndexOf('.'));
    }

    /**
     * Saves the input array as a .wav file
     *
     * @param data  sine input array
     * @param title future file name
     */
    private void saveAsWav(double[] data, String title) {
        byte[] byteData = new byte[data.length];
        for (int i = 0; i < data.length - 1; i++) {
            int aux = (int) Math.floor(data[i]);
            byteData[i] = (byte) (aux);
            byteData[i + 1] = (byte) (aux >> 8);
        }
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        try (AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(byteData), format,
                byteData.length / format.getFrameSize())) {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
                    new File(title + "." + AudioFileFormat.Type.WAVE.getExtension()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets all values to default
     */
    public void reset() {
        image = null;
        width = height = 0;
    }

    /**
     * Creates an audio track of a certain length
     *
     * @param length  length of track in sec
     * @param quality quality of conversion
     * @return double array of certain length that represents an image
     */
    private double[] createAudio(ImageConversionQuality quality, int length) {
        double[] convertedImage = convertImageToSine(image, quality.getQuality());
        double[] dataToSave = new double[(int) (44100 * length * 2.1)];
        int amount = dataToSave.length / convertedImage.length;
        if (amount == 0) return dataToSave;
        int index = 0;
        for (int i = 0; i < convertedImage.length / 256; i++) {
            for (int j = 0; j < amount; j++) {
                for (int k = 0; k < 256; k++) {
                    dataToSave[index++] = convertedImage[i * 256 + k];
                }
            }

        }

        return dataToSave;
    }
}