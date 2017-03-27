package com.danidemi.templategeneratormavenplugin.demo.juel;

/*-
 * #%L
 * template-generator-maven-plugin
$Id:$
$HeadURL:$
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.junit.Test;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JuelDemo {

    @Test
    public void shouldEvaluateComplexExpressions() {

        ExpressionFactory factory = new ExpressionFactoryImpl();

        String[] args = new String[]{
                "a",
                "bb",
                "ccc",
                "dddd"
        };

        {
            String expression = "${args[3].length() > 2 && args[2].length() > 1}";
            SimpleContext context = new SimpleContext();
            context.setVariable("args", factory.createValueExpression(args, String[].class));
            ValueExpression e = factory.createValueExpression(context, expression, Boolean.class);
            Object value = e.getValue(context);
            assertThat("arg is '" + args + "'", ((Boolean) value).booleanValue(), is(true));
        }

        {
            String expression = "${args[3].length() > 200 && args[2].length() > 100}";
            SimpleContext context = new SimpleContext();
            context.setVariable("args", factory.createValueExpression(args, String[].class));
            ValueExpression e = factory.createValueExpression(context, expression, Boolean.class);
            Object value = e.getValue(context);
            assertThat("arg is '" + args + "'", ((Boolean) value).booleanValue(), is(false));
        }

    }

    @Test
    public void shouldEvaluateAnExpressionRepeatedly() throws NoSuchMethodException {

        ExpressionFactory factory = new ExpressionFactoryImpl();

        String[] args = new String[]{
                "a",
                "bb",
                "ccc",
                "dddd"
        };

        String expression = "${arg.length() > 2}";
        for (String arg : args) {
            SimpleContext context = new SimpleContext();
            context.setVariable("arg", factory.createValueExpression(arg, String.class));
            ValueExpression e = factory.createValueExpression(context, expression, Boolean.class);
            Object value = e.getValue(context);
            assertThat( "arg is '" + arg + "'", ((Boolean)value).booleanValue(), is(arg.length() > 2) );
        }

    }

    @Test
    public void shouldEvaluateAnExpressionThatUsesCustomFunctions() throws NoSuchMethodException {

        // the ExpressionFactory implementation is de.odysseus.el.ExpressionFactoryImpl
        ExpressionFactory factory = new de.odysseus.el.ExpressionFactoryImpl();

        // package de.odysseus.el.util provides a ready-to-use subclass of ELContext
        de.odysseus.el.util.SimpleContext context = new de.odysseus.el.util.SimpleContext();

        // map function math:max(int, int) to java.lang.Math.max(int, int)
        context.setFunction("math", "max", Math.class.getMethod("max", int.class, int.class));

        // map variable foo to 0
        context.setVariable("foo", factory.createValueExpression(0, int.class));

        // parse our expression
        ValueExpression e = factory.createValueExpression(context, "${math:max(foo,bar)}", int.class);

        // set value for top-level property "bar" to 1
        factory.createValueExpression(context, "${bar}", int.class).setValue(context, 1);

        // get value for our expression
        assertThat( e.getValue(context), equalTo(1) );

    }

}
