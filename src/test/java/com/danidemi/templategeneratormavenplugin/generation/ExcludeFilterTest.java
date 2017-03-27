package com.danidemi.templategeneratormavenplugin.generation;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ExcludeFilterTest {

    @Test
    public void shouldDiscardSomethingKept() {
        assertThat( new ExcludeFilter(context -> true).keep(new HashMap<>()), is(false) );
    }

    @Test
    public void shouldKeepSomethingDiscarded() {
        assertThat( new ExcludeFilter(context -> false).keep(new HashMap<>()), is(true) );
    }

}