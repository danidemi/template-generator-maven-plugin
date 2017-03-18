package com.danidemi.templategeneratormavenplugin.generation;

import java.io.*;

/**
 * Store a generated source.
 */
public class FileStore {

    private final File folder;

    public FileStore(File folder) {
        this.folder = folder;
    }

    public void store(StringWriter content) {
        try {
            File file = new File( folder, "thepath");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = content.toString().getBytes();
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
