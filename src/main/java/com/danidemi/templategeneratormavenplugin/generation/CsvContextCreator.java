package com.danidemi.templategeneratormavenplugin.generation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Creates one context for each line in a CSV.
 */
public class CsvContextCreator implements ContextCreator {

    private final String resourcePath;

    public CsvContextCreator(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override public Iterator<Map<String, Object>> iterator() {
        try {
            Reader in = new FileReader(getClass().getResource(resourcePath).getFile());
            CSVParser parse = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            Iterator<CSVRecord> iterator = parse.iterator();

            // get the headers
            List<String> headersAsList = new ArrayList<>( parse.getHeaderMap().keySet() );

            return new IteratorAdapter< CSVRecord, Map<String, Object> >(iterator,
                    (record) -> {
                        HashMap mapped = new HashMap<Object, String>();
                        headersAsList.forEach( header -> mapped.put(header, record.get(header)) );
                        return mapped;
                    }
            );

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

    private static class IteratorAdapter<Original, Tranformed> implements Iterator<Tranformed> {

        private final Iterator<Original> iterator;
        private final Function<Original, Tranformed> mappingFunction;

        private IteratorAdapter(Iterator<Original> iterator, Function<Original, Tranformed> mappingFunction) {
            this.iterator = iterator;
            this.mappingFunction = mappingFunction;
        }

        @Override public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override public Tranformed next() {
            return mappingFunction.apply( iterator.next() );
        }
    }
}
