package com.github.vladyslavbabenko.imagetosound.javafx.enums;

public enum ImageExtension {
    JPEG("*.jpeg"),
    JPG("*.jpg"),
    GIF("*.gif"),
    TIFF("*.tiff"),
    TIF("*.tif"),
    PNG("*.png");

    private final String extension;

    ImageExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "ImageExtension{" +
                "extension='" + extension + '\'' +
                '}';
    }
}