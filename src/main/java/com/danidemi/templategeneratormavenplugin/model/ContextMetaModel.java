package com.danidemi.templategeneratormavenplugin.model;

import java.util.LinkedHashSet;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;

public class ContextMetaModel {

    private final FileModel template;
    private final FileModel source;
    private final FileModel target;
    private final CountModel count;
    private final LinkedHashSet<String> tags;

    public ContextMetaModel(FileModel template, FileModel source, FileModel target, CountModel count, LinkedHashSet<String> tags) {

        checkArgument(template!=null );
        checkArgument(source!=null );
        checkArgument(target!=null );
        checkArgument(count!=null );
        checkArgument(tags!=null );

        this.template = template;
        this.source = source;
        this.target = target;
        this.count = count;
        this.tags = tags;

    }

    public FileModel getTemplate() {
        return template;
    }

    public FileModel getSource() {
        return source;
    }

    public FileModel getTarget() {
        return target;
    }

    public CountModel getCount() {
        return count;
    }

    public LinkedHashSet<String> getTags() {
        return tags;
    }
}
