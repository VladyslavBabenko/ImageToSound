package com.github.vladyslavbabenko.imagetosound.javafx.enums;

public enum ImageConversionQuality {
    LOW(256),
    MID(512),
    GOOD(1024),
    VERY_GOOD(2048);

    private final int quality;

    ImageConversionQuality(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }
}