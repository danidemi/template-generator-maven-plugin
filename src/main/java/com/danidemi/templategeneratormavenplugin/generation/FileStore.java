package com.danidemi.templategeneratormavenplugin.generation;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

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
