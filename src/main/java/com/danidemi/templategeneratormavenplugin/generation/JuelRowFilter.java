package com.danidemi.templategeneratormavenplugin.generation;

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

    @Override public boolean keep(Map<String, Object> contextt) {
        SimpleContext context = new SimpleContext();

        contextt.entrySet().forEach( (e)->{
            String key = e.getKey();
            Object value = e.getValue();
            context.setVariable(key, factory.createValueExpression(value, value.getClass()));
        } );

        ValueExpression e = factory.createValueExpression(context, includeRowExpression, Boolean.class);
        boolean result = (Boolean) e.getValue(context);

        return result;

    }
}
