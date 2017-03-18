package com.danidemi.templategeneratormavenplugin.generation;

import org.codehaus.plexus.util.FileUtils;

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
        folder.mkdirs();
        try {
            File file = new File( folder, "thepath." + (int)(Math.random()*100) + ".java");
//            FileOutputStream fos = new FileOutputStream(file);
//            byte[] bytes = content.toString().getBytes();
//            fos.write(bytes);
//            fos.flush();
//            fos.close();

            FileUtils.fileWrite(file, content.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
