package com.danidemi.templategeneratormavenplugin.generation;

import java.io.*;

public class Storage {
    public void store(StringWriter content) {
        try {
            File file = new File("thepath");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = content.toString().getBytes();
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
