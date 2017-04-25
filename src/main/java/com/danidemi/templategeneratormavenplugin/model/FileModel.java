package com.danidemi.templategeneratormavenplugin.model;

import java.io.File;

public class FileModel {

    private final File file;

    FileModel(File file) {
        this.file = file;
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public String getFileName() {
        return file.getName();
    }

}
