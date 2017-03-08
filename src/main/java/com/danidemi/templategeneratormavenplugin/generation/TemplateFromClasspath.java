package com.danidemi.templategeneratormavenplugin.generation;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class TemplateFromClasspath {

    private final String templateLocator;

    public TemplateFromClasspath(String templateLocator) {
        this.templateLocator = templateLocator;
    }

    Reader asReader() {
        InputStreamReader inputStreamReader = null;
        try {
            URL resource = getClass().getResource(templateLocator);
            String file = new File( resource.getFile() ).getAbsolutePath();
            inputStreamReader = new InputStreamReader(resource.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputStreamReader;
    }

}
