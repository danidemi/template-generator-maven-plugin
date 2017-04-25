package com.danidemi.templategeneratormavenplugin.generation.impl;

import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CsvRowSourceTest {

    @Test public void testRows() {

        // given
        CsvRowSource sut = new CsvRowSource(new InputStreamReader(Object.class.getResourceAsStream("/codeAndCurrency.csv")));

        // when
        IRowModel next = sut.iterator().next();

        // then
        assertThat( next.getMeta().getIndex(), equalTo(0L) );
        assertThat( next.getMeta().getCount(), equalTo(1L) );
    }

}