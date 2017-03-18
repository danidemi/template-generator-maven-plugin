package com.danidemi.templategeneratormavenplugin.generation;

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

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;
import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.validateArgumentNotNull;

/**
 * Creates one context for each line in a CSV.
 */
public class CsvContextCreator implements ContextCreator {

    private final String filePath;

    private CsvContextCreator(String resourcePath) {
        checkArgument(resourcePath != null && !resourcePath.trim().isEmpty(), "File '%s' not valid.", resourcePath);
        this.filePath = resourcePath;
    }

    public static CsvContextCreator fromClasspath(String resourcePath) {
        URL resource = CsvContextCreator.class.getResource(resourcePath);
        checkArgument(resource != null, "Resource '%s' does not exist.", resourcePath);
        String file = resource.getFile();
        return new CsvContextCreator(file);
    }

    public static CsvContextCreator fromFilepath(String resourcePath) {
        File f = new File(resourcePath);
        checkArgument(f.exists(), "File '%s' does not exist.", resourcePath);
        checkArgument(f.canRead(), "File '%s' is not readable.", resourcePath);
        return new CsvContextCreator(f.getAbsolutePath());
    }

    @Override public Iterator<Map<String, Object>> iterator() {
        try {

            // get the reader from the resource
            Reader in = new FileReader(filePath);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            Iterator<CSVRecord> iterator = parser.iterator();

            // get the headers
            List<String> headersAsList = new ArrayList<>( parser.getHeaderMap().keySet() );

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
