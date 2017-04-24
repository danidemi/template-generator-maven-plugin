package com.danidemi.templategeneratormavenplugin.model;

import java.util.LinkedHashSet;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;

public class ContextMetaModel {

    private final FileModel template;
    private final FileModel source;
    private CountModel count;
    private LinkedHashSet<String> tags;

    ContextMetaModel(FileModel template, FileModel source) {

        checkArgument(template!=null );
        checkArgument(source!=null );

        this.template = template;
        this.source = source;

    }

    void setCount(CountModel count) {
        this.count = count;
    }

    void setTags(LinkedHashSet<String> tags) {
        this.tags = tags;
    }

    public FileModel getTemplate() {
        return template;
    }

    public FileModel getSource() {
        return source;
    }

    public CountModel getCount() {
        return count;
    }

    public LinkedHashSet<String> getTags() {
        return tags;
    }
}
