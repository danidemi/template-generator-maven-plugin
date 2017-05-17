package com.danidemi.templategeneratormavenplugin.generation.impl;

/*-
 * #%L
 * template-generator-maven-plugin
$Id:$
$HeadURL:$
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

import com.danidemi.templategeneratormavenplugin.generation.RowSource;
import com.danidemi.templategeneratormavenplugin.model.IRowMetaModel;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import com.danidemi.templategeneratormavenplugin.utils.TransformIteratorAdapter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;

public class CsvRowSource implements RowSource {

    private final Reader reader;

    public CsvRowSource(Reader reader) {
        this.reader = reader;
    }

    @Override public Iterator<IRowModel> iterator() {

        try {
            // get the reader from the resource
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);

            // get the headers
            List<String> headersAsList = new ArrayList<>( parser.getHeaderMap().keySet() );

            return new TransformIteratorAdapter<CSVRecord, IRowModel>(
                    parser.iterator(),
                    new Function<CSVRecord, IRowModel>() {
                        @Override
                        public IRowModel apply(CSVRecord r) {
                            return new CsvRowModel(r, headersAsList);
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static class CsvRowModel implements IRowModel {

        private final CSVRecord record;
        private final List<String> headersAsList;

        public CsvRowModel(CSVRecord record, List<String> headersAsList) {
            this.record = record;
            this.headersAsList = headersAsList;
        }

        @Override public Map<String, Object> getData() {
            HashMap mapped = new HashMap<Object, String>();
            for (String header : headersAsList) {
                mapped.put(header, record.get(header));
            }
            return mapped;
        }

        @Override public IRowMetaModel getMeta() {
            return new IRowMetaModel() {
                @Override public long getCount() {

                    return record.getRecordNumber();
                }

                @Override public long getIndex() {

                    return record.getRecordNumber()-1;
                }

                @Override public long getSourceIndex() {

                    return record.getRecordNumber()-1;
                }

                @Override public long getSourceCount() {

                    return record.getRecordNumber();
                }
            };
        }

    }

}
