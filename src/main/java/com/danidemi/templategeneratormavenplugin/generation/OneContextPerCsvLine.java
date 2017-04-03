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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;

/**
 * Creates one context for each line in a CSV.
 */
public class OneContextPerCsvLine implements ContextCreator {

    private final String filePath;
    private final RowFilter rowFilter;

    private OneContextPerCsvLine(String resourcePath, RowFilter rowFilter) {
        checkArgument(resourcePath != null && !resourcePath.trim().isEmpty(), "File '%s' not valid.", resourcePath);
        this.filePath = resourcePath;
        this.rowFilter = rowFilter;
    }

    public static OneContextPerCsvLine fromClasspath(String resourcePath, RowFilter rowFilter) {
        URL resource = OneContextPerCsvLine.class.getResource(resourcePath);
        checkArgument(resource != null, "Resource '%s' does not exist.", resourcePath);
        String file = resource.getFile();
        return new OneContextPerCsvLine(file, rowFilter);
    }

    public static OneContextPerCsvLine fromFilepath(String resourcePath, RowFilter rowFilter) {
        File f = new File(resourcePath);
        checkArgument(f.exists(), "File '%s' (resolved to '%s') does not exist.", resourcePath, f.getAbsolutePath());
        checkArgument(f.canRead(), "File '%s' (resolved to '%s') is not readable.", resourcePath, f.getAbsolutePath());
        return new OneContextPerCsvLine(f.getAbsolutePath(), rowFilter);
    }

    @Override public Iterator<Map<String, Object>> iterator() {
        try {

            // get the reader from the resource
            Reader in = new FileReader(filePath);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            Iterator<CSVRecord> iterator = parser.iterator();

            // get the headers
            List<String> headersAsList = new ArrayList<>( parser.getHeaderMap().keySet() );

            TransformIteratorAdapter<CSVRecord, Map<String, Object>> iterator1 = new TransformIteratorAdapter<>(iterator,
                    new Function<CSVRecord, Map<String, Object>>() {
                        @Override
                        public Map<String, Object> apply(CSVRecord record) {
                            HashMap mapped = new HashMap<Object, String>();
                            headersAsList.forEach(header -> mapped.put(header, record.get(header)));
                            return mapped;
                        }
                    }
            );

            FilterIteratorAdapter<Map<String, Object>> iterator2 = new FilterIteratorAdapter<>(iterator1, new Predicate<Map<String, Object>>() {
                @Override
                public boolean test(Map<String, Object> stringObjectMap) {
                    boolean keep = rowFilter.keep(stringObjectMap);
                    return keep;
                }
            });

            return iterator2;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override public void forEach(Consumer<? super Map<String, Object>> action) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override public Spliterator<Map<String, Object>> spliterator() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

}
