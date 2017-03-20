package com.danidemi.templategeneratormavenplugin.generation;

import org.codehaus.plexus.util.FileUtils;

import java.io.*;

/**
 * Store a generated source.
 */
public class FileStore {

    private final File folder;

    public FileStore(File baseFolder) {
        this.folder = baseFolder;
    }

    public void storeContentToFile(StringWriter content, String fileName) {

        String completePath = folder.getAbsolutePath() + (fileName.startsWith(File.separator) ? "" : File.separator) + fileName;

        try {
            File file = new File( completePath );
            File dir = file.getParentFile();
            dir.mkdirs();
            FileUtils.fileWrite(file, content.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
