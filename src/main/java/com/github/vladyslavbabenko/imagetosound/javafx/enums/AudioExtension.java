package com.github.vladyslavbabenko.imagetosound.javafx.enums;

public enum AudioExtension {
    M4A("*.m4a"),
    FLAC("*.flac"),
    MP3("*.mp3"),
    MP4("*.mp4"),
    WAV("*.wav"),
    WMA("*.wma"),
    AAC("*.aac");

    private final String extension;

    AudioExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "AudioExtension{" +
                "extension='" + extension + '\'' +
                '}';
    }
}