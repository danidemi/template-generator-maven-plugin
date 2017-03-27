package com.danidemi.templategeneratormavenplugin.generation;

import com.danidemi.templategeneratormavenplugin.generation.RowFilter;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Map;

public class JuelRowFilter implements RowFilter {

    private final String includeRowExpression;
    private final ExpressionFactory factory = new ExpressionFactoryImpl();

    public JuelRowFilter(String includeRowExpression) {
        this.includeRowExpression = includeRowExpression;
    }

    @Override public boolean discard(Map<String, Object> contextt) {
        SimpleContext context = new SimpleContext();

        contextt.entrySet().forEach( (e)->{
            context.setVariable(e.getKey(), factory.createValueExpression(e.getValue(), e.getValue().getClass()));
        } );

        ValueExpression e = factory.createValueExpression(context, includeRowExpression, Boolean.class);
        return !(Boolean) e.getValue(context);

    }
}
