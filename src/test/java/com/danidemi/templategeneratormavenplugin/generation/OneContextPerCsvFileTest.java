package com.danidemi.templategeneratormavenplugin.generation;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class OneContextPerCsvFileTest {

    @Test(expected = IllegalArgumentException.class) public void failWhenSourceDoesNotExist() {
        OneContextPerCsvFile sut = OneContextPerCsvFile.fromClasspath("/does-not-exists");
    }

    @Test public void produceContextFromAnotherCsvFile() {

        OneContextPerCsvFile sut = OneContextPerCsvFile.fromClasspath("/codeAndCurrency.csv");

        Iterator<Map<String, Object>> ctxIt = sut.iterator();

        Map<String, Object> ctx;
        ctx = ctxIt.next();

        assertThat( "Keys are:" + ctx.keySet(), ctx.containsKey("File"), is(true));
        assertThat( ctx.get("TotalRows"), equalTo(3) );
        assertThat( ctx.get("LastIndex"), equalTo(2) );


        List< Map<String, Object> > rows = (List<Map<String, Object>>) ctx.get("File");

        ctx = rows.get(0);
        assertThat( ctx.get("RowIndex"), equalTo(0) );
        assertThat( ctx.get("RowCount"), equalTo(1) );
        assertThat( ctx.get("Code"), equalTo("EUR") );
        assertThat( ctx.get("Currency"), equalTo("Euro") );

        ctx = rows.get(1);
        assertThat( ctx.get("RowIndex"), equalTo(1) );
        assertThat( ctx.get("RowCount"), equalTo(2) );
        assertThat( ctx.get("Code"), equalTo("USD") );
        assertThat( ctx.get("Currency"), equalTo("Dollar") );

        ctx = rows.get(2);
        assertThat( ctx.get("RowIndex"), equalTo(2) );
        assertThat( ctx.get("RowCount"), equalTo(3) );
        assertThat( ctx.get("Code"), equalTo("GBP") );
        assertThat( ctx.get("Currency"), equalTo("Pound") );

        assertThat(ctxIt.hasNext(), is(false));

    }

}