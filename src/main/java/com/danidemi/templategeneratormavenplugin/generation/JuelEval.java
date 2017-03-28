package com.danidemi.templategeneratormavenplugin.generation;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Map;

class JuelEval<T> {

    private final ExpressionFactory factory = new ExpressionFactoryImpl();

    public T invoke(Map<String, Object> contextt, String includeRowExpression) {
        SimpleContext context = new SimpleContext();

        contextt.entrySet().forEach( (e)->{
            String key = e.getKey();
            Object value = e.getValue();
            context.setVariable(key, factory.createValueExpression(value, value.getClass()));
        } );

        ValueExpression e = factory.createValueExpression(context, includeRowExpression, Object.class);
        return (T) e.getValue(context);
    }
}
