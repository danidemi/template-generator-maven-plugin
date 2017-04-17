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
import com.danidemi.templategeneratormavenplugin.model.RowModel;
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
 * This implementation does not need to load the whole CSV in memory but for that reasons some meta data are not available.
 */
public class OneContextPerCsvLineStaxLike implements ContextCreator {

    private final String filePath;
    private final RowFilter rowFilter;

    private OneContextPerCsvLineStaxLike(String resourcePath, RowFilter rowFilter) {
        checkArgument(resourcePath != null && !resourcePath.trim().isEmpty(), "File '%s' not valid.", resourcePath);
        this.filePath = resourcePath;
        this.rowFilter = rowFilter;
    }

    public static OneContextPerCsvLineStaxLike fromClasspath(String resourcePath, RowFilter rowFilter) {
        URL resource = OneContextPerCsvLineStaxLike.class.getResource(resourcePath);
        checkArgument(resource != null, "Resource '%s' does not exist.", resourcePath);
        String file = resource.getFile();
        return new OneContextPerCsvLineStaxLike(file, rowFilter);
    }

    public static OneContextPerCsvLineStaxLike fromFilepath(String resourcePath, RowFilter rowFilter) {
        File f = new File(resourcePath);
        checkArgument(f.exists(), "File '%s' (resolved to '%s') does not exist.", resourcePath, f.getAbsolutePath());
        checkArgument(f.canRead(), "File '%s' (resolved to '%s') is not readable.", resourcePath, f.getAbsolutePath());
        return new OneContextPerCsvLineStaxLike(f.getAbsolutePath(), rowFilter);
    }

    @Override public Iterable<ContextModel> contexts() {
        return new Iterable<ContextModel>() {
            @Override public Iterator<ContextModel> iterator() {
                try {

                    // get the reader from the resource
                    Reader in = new FileReader(filePath);
                    CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

                    Iterator<CSVRecord> iterator = parser.iterator();

                    // get the headers
                    List<String> headersAsList = new ArrayList<>( parser.getHeaderMap().keySet() );

                    TransformIteratorAdapter<CSVRecord, ContextModel> iterator1 = new TransformIteratorAdapter<>(iterator,
                            new Function<CSVRecord, ContextModel>() {
                                @Override
                                public ContextModel apply(CSVRecord record) {

                                    HashMap mapped = new HashMap<Object, String>();
                                    headersAsList.forEach(header -> mapped.put(header, record.get(header)));

                                    ContextModelBuilder builder = new ContextModelBuilder();
                                    builder.toRows().add(mapped, record.getRecordNumber());
                                    return builder.build();
                                }
                            }
                    );

                    FilterIteratorAdapter<ContextModel> iterator2 = new FilterIteratorAdapter<>(iterator1, new Predicate<ContextModel>() {
                        @Override
                        public boolean test(ContextModel stringObjectMap) {
                            boolean keep = rowFilter.keep(stringObjectMap);
                            return keep;
                        }
                    });

                    return iterator2;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
