package com.danidemi.templategeneratormavenplugin.maven;

import com.danidemi.templategeneratormavenplugin.generation.IncludeAllRowFilter;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IncludeAllRowFilterTest {

    @Test public void shouldNotDiscardRows() {

        IncludeAllRowFilter sut = new IncludeAllRowFilter();

        assertThat( sut.discard(null), is(false) );
        assertThat( sut.discard(new HashedMap()), is(false) );

    }

}