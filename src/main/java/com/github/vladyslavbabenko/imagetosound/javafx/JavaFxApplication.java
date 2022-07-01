package com.github.vladyslavbabenko.imagetosound.javafx;

import com.github.vladyslavbabenko.imagetosound.ImageToSoundApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder().sources(ImageToSoundApplication.class).run(args);
    }

    @Override
    public void start(Stage stage) {
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}