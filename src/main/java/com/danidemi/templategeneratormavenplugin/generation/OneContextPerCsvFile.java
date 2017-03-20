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

    public static OneContextPerCsvFile fromFilepath(String resourcePath) {
        File f = new File(resourcePath);
        checkArgument(f.exists(), "File '%s' does not exist.", resourcePath);
        checkArgument(f.canRead(), "File '%s' is not readable.", resourcePath);
        return new OneContextPerCsvFile(f.getAbsolutePath());
    }

    @Override public Iterator<Map<String, Object>> iterator() {
        try {

            // get the reader from the resource
            Reader in = new FileReader(filePath);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            // get the headers
            List<String> headersAsList = new ArrayList<>( parser.getHeaderMap().keySet() );


            ArrayList<Map<String, Object>> ctx = new ArrayList<>();

            int index = 0;
            for(CSVRecord record : parser){
                HashMap mapped = new HashMap<Object, String>();
                headersAsList.forEach( header -> mapped.put(header, record.get(header)) );

                mapped.put("RowIndex", index);
                mapped.put("RowCount", index+1);

                ctx.add( mapped );
                index++;
            }

            HashMap<String, Object> r = new HashMap<>();
            r.put("File", ctx);
            r.put("TotalRows", index);
            r.put("LastIndex", index-1);

            List<Map<String, Object>> rr = new ArrayList<>();
            rr.add(r);

            return rr.iterator();

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
