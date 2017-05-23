package com.danidemi.templategeneratormavenplugin.model;

import com.danidemi.templategeneratormavenplugin.maven.GenerateMojo;

import java.util.Map;

public abstract class RowModelUtils {

    public static final String describe(IRowModel rowModel){
        Map<String, Object> data = rowModel.getData();
        IRowMetaModel meta = rowModel.getMeta();
        String reult = data.toString();
        reult = reult.substring(1, reult.length()-1);
        reult +=  " | count=" + meta.getCount() + ", sourceCount=" + meta.getSourceCount();
        return "{" + reult + "}";
    }

    public static final String describe(ContextModel model){
        StringBuilder builder = new StringBuilder();
        builder.append("rows").append("\n");
        for (int i=0; i< model.getRows().size(); i++) {
            builder.append( " - " + i + ")").append(describe(model.getRows().get(i)) ).append("\n");
        }
        ContextMetaModel meta = model.getMeta();
        builder.append("meta").append("\n");
        builder.append(" - count:").append(meta.getCount() != null ? meta.getCount().getLastIndex() + "/" + meta.getCount().getRows() : String.valueOf(null)).append("\n");
        builder.append(" - source:").append(meta.getSource()!=null ? meta.getSource().getAbsolutePath() : "<null>" ).append("\n");
        builder.append(" - tags:").append( meta.getTags()!=null ? meta.getTags() : "<null>" ).append("\n");
        builder.append(" - template:").append( meta.getTemplate()!=null ? meta.getTemplate().getAbsolutePath() : "<null>" ).append("\n");
        return builder.toString();
    }

    private RowModelUtils() {
        throw new UnsupportedOperationException("Not meant to be instantiated");
    }

}