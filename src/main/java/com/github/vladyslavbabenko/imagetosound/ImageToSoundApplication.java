package com.github.vladyslavbabenko.imagetosound;

import com.github.vladyslavbabenko.imagetosound.javafx.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageToSoundApplication {
    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }
}