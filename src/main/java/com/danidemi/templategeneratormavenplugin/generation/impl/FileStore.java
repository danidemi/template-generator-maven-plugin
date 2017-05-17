package com.danidemi.templategeneratormavenplugin.generation.impl;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Store a generated source.
 */
public class FileStore {

    private final File folder;

    public FileStore(File baseFolder) {
        this.folder = baseFolder;
    }

    public File storeContentToFile(StringWriter content, String fileName) {
        File file = fileForFilename(fileName);
        return _storeContentToFile(content, file);
    }

    public File storeContentToFile(StringWriter content, File file) {
        if(!file.getAbsolutePath().startsWith(folder.getAbsolutePath())) {
            throw new IllegalArgumentException( "File '" + file.getAbsolutePath() + "' is not under this store root '" + folder.getAbsolutePath() + "'." );
        }
        return _storeContentToFile(content, file);
    }

    private File _storeContentToFile(StringWriter content, File file) {
        File dir = file.getParentFile();
        try{
            dir.mkdirs();
            FileUtils.fileWrite(file, content.toString());
            return file;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The {@link File} corresponding to a filename to be stored under the base folder.
     */
    public File fileForFilename(String fileName) {
        String completePath = folder.getAbsolutePath() + (fileName.startsWith(File.separator) ? "" : File.separator) + fileName;
        return new File(completePath);
    }

}
