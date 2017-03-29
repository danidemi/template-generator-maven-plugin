package com.danidemi.templategeneratormavenplugin.generation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class OneContextPerTag implements ContextCreator {

    private final RowFilter rowFilter;
    private final File file;
    private final List<String> tagExpressions;

    public OneContextPerTag( String pathToCsv, RowFilter rowFilter ) {
        this.file = new File(pathToCsv);
        checkArgument(this.file.exists(), "File '%s' does not exist.", pathToCsv);
        checkArgument(this.file.canRead(), "File '%s' is not readable.", pathToCsv);
        this.rowFilter = checkNotNull( rowFilter );
        this.tagExpressions = new ArrayList<>();
    }

    @Override public Iterator<Map<String, Object>> iterator() {
        try {
            // get the reader from the resource
            Reader in = new FileReader(file);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            Iterator<CSVRecord> iterator = parser.iterator();

            // get the headers
            List<String> headersAsList = new ArrayList<>(parser.getHeaderMap().keySet());

            TransformIteratorAdapter<CSVRecord, Map<String, Object>> iterator1 = new TransformIteratorAdapter<>(iterator, new Function<CSVRecord, Map<String, Object>>() {
                @Override public Map<String, Object> apply(CSVRecord record) {
                    HashMap mapped = new HashMap<Object, String>();
                    headersAsList.forEach(header -> mapped.put(header, record.get(header)));
                    return mapped;
                }
            });

            FilterIteratorAdapter<Map<String, Object>> iterator2 = new FilterIteratorAdapter<>(iterator1, new Predicate<Map<String, Object>>() {
                @Override
                public boolean test(Map<String, Object> stringObjectMap) {
                    boolean keep = rowFilter.keep(stringObjectMap);
                    return keep;
                }
            });

            Map<String, List<Map<String, Object>>> tag2context = new LinkedHashMap<>();
            while(iterator2.hasNext()){
                Map<String, Object> row = iterator2.next();

                Set<String> tagsForRow = new HashSet<>();

                for (String tagExpression : tagExpressions) {
                    tagExpression = tagExpression.replace("@{", "${");
                    String tag = new JuelEval<String>().invoke(row, tagExpression);
                    tagsForRow.add(tag);
                }

                for (String tagForRow : tagsForRow) {
                    if(!tag2context.containsKey(tagForRow)){
                        tag2context.put(tagForRow, new ArrayList<>());
                    }
                    tag2context.get(tagForRow).add(row);
                }

            }

            List<Map<String, Object>> ctxs = new ArrayList<>();
            for (Map.Entry<String, List<Map<String, Object>>> stringListEntry : tag2context.entrySet()) {

                HashMap<String, Object> ctx = new HashMap<>();
                ctx.put("File", stringListEntry.getValue());

                ctxs.add(ctx);
            }

            return ctxs.iterator();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void addTagExpression(String expression) {
        this.tagExpressions.add(expression);
    }
}
