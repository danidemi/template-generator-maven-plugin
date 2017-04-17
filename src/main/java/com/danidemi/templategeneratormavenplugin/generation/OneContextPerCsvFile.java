package com.danidemi.templategeneratormavenplugin.generation;

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

import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.ContextModelBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;
import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.validateArgumentNotNull;

/**
 * Creates one context for the whole file.
 */
public class OneContextPerCsvFile implements ContextCreator {

    private final String filePath;

    private OneContextPerCsvFile(String resourcePath) {
        checkArgument(resourcePath != null && !resourcePath.trim().isEmpty(), "File '%s' not valid.", resourcePath);
        this.filePath = resourcePath;
    }

    public static OneContextPerCsvFile fromClasspath(String resourcePath) {
        URL resource = OneContextPerCsvFile.class.getResource(resourcePath);
        checkArgument(resource != null, "Resource '%s' does not exist.", resourcePath);
        String file = resource.getFile();
        return new OneContextPerCsvFile(file);
    }

    public static OneContextPerCsvFile fromFilepath(String resourcePath, RowFilter rowFilter) {
        File f = new File(resourcePath);
        checkArgument(f.exists(), "File '%s' does not exist.", resourcePath);
        checkArgument(f.canRead(), "File '%s' is not readable.", resourcePath);
        return new OneContextPerCsvFile(f.getAbsolutePath());
    }

    @Override public Iterable<ContextModel> contexts() {
        ContextModelBuilder builder = new ContextModelBuilder();

        try {
            // get the reader from the resource
            Reader in = new FileReader(filePath);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            // get the headers
            List<String> headersAsList = new ArrayList<>( parser.getHeaderMap().keySet() );

            for(CSVRecord record : parser){
                HashMap mapped = new HashMap<Object, String>();
                headersAsList.forEach( header -> mapped.put(header, record.get(header)) );
                builder.toRows().add( mapped, record.getRecordNumber() );
            }

            return Arrays.asList( builder.build() );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new iterator based on a given one, where elements are obtained applying a transformation to the original ones.
     * @param <Original>    The type of the items from the original iterator.
     * @param <Tranformed>  The type of the items in the transformed iterator.
     */
    private static class IteratorAdapter<Original, Tranformed> implements Iterator<Tranformed> {

        private final Iterator<Original> iterator;
        private final Function<Original, Tranformed> mappingFunction;

        private IteratorAdapter(Iterator<Original> iterator, Function<Original, Tranformed> mappingFunction) {
            this.iterator = validateArgumentNotNull( iterator );
            this.mappingFunction = validateArgumentNotNull( mappingFunction );
        }

        @Override public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override public Tranformed next() {
            return mappingFunction.apply( iterator.next() );
        }
    }
}
