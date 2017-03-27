package com.danidemi.templategeneratormavenplugin.maven;

import com.danidemi.templategeneratormavenplugin.generation.JuelRowFilter;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JuelRowFilterTest {

    @Test public void shouldDiscardContextNotSatyisfyingExpression() {

        JuelRowFilter sut = new JuelRowFilter("${a+a>10}");

        HashedMap context = newContextWithA(3);

        boolean discard = sut.keep(context);

        assertThat( discard, is(false) );

    }

    @Test public void shouldNotDiscardContextSatyisfyingExpression() {

        JuelRowFilter sut = new JuelRowFilter("${a<10}");

        HashedMap context = newContextWithA(5);

        boolean discard = sut.keep(context);

        assertThat( discard, is(true) );

    }

    private HashedMap newContextWithA(int a) {
        HashedMap context = new HashedMap();
        context.put("a", a);
        return context;
    }

}