package com.danidemi.templategeneratormavenplugin.generation.impl;

/*-
 * #%L
 * template-generator-maven-plugin
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Map;

public class JuelEval<T> {

    private final ExpressionFactory factory = new ExpressionFactoryImpl();

    public T invoke(String expression, String key, Object obj){
        SimpleContext juelContext = new SimpleContext();
        juelContext.setVariable(key, factory.createValueExpression(obj, obj.getClass()));
        ValueExpression e = factory.createValueExpression(juelContext, expression, Object.class);
        return (T) e.getValue(juelContext);
    }

    public T invoke(ContextModel context, String includeRowExpression) {
        Map<String, Object> rowAsMap = context.asMap();
        Object result = eval(includeRowExpression, rowAsMap);
        return (T) result;
    }

    public T invoke(Map<String, Object> row, String includeRowExpressionssion) {
        Object result = eval(includeRowExpressionssion, row);
        return (T) result;
    }

    private Object eval(String expression, Map<String, Object> map) {
        SimpleContext juelContext = new SimpleContext();
        map.entrySet().forEach( (e)->{
            String key = e.getKey();
            Object value = e.getValue();
            juelContext.setVariable(key, factory.createValueExpression(value, value.getClass()));
        } );
        ValueExpression e = factory.createValueExpression(juelContext, expression, Object.class);
        return e.getValue(juelContext);
    }


}
