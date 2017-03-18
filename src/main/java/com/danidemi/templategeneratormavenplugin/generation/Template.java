package com.danidemi.templategeneratormavenplugin.generation;

import java.io.*;
import java.net.URL;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;

public class Template {

    private final File templateLocator;

    private Template(File templateLocator) {
        checkArgument(templateLocator!=null, "Null argument.");
        checkArgument(templateLocator.exists(), "File %s does not exist.", templateLocator.getAbsoluteFile());
        checkArgument(templateLocator.canRead(), "File %s is not readable.", templateLocator.getAbsoluteFile());
        this.templateLocator = templateLocator;
    }

    public static Template fromClasspath(String templateLocator){
        URL resource = Template.class.getResource(templateLocator);
        File file = new File( resource.getFile() );
        return new Template(file);
    }

    public static Template fromFilePath(String templateLocator){
        File file = new File( templateLocator );
        return new Template(file);
    }

    public Reader asReader() {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader( new FileInputStream( templateLocator ) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputStreamReader;
    }

}
