package com.danidemi.templategeneratormavenplugin.generation;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CsvContextCreatorTest {

    @Test public void produceContextFromAnotherCsvFile() {

        CsvContextCreator sut = new CsvContextCreator("/codeAndCurrency.csv");

        Iterator<Map<String, Object>> ctxIt = sut.iterator();

        Map<String, Object> ctx;
        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("EUR") );
        assertThat( ctx.get("Currency"), equalTo("Euro") );

        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("USD") );
        assertThat( ctx.get("Currency"), equalTo("Dollar") );

        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("GBP") );
        assertThat( ctx.get("Currency"), equalTo("Pound") );

        assertThat(ctxIt.hasNext(), is(false));

    }

    @Test public void produceAContextForEachRowInCsvFile() {

        CsvContextCreator sut = new CsvContextCreator("/codeAndCountry.csv");

        Iterator<Map<String, Object>> ctxIt = sut.iterator();

        Map<String, Object> ctx;
        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("IT") );
        assertThat( ctx.get("Country"), equalTo("Italy") );

        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("DE") );
        assertThat( ctx.get("Country"), equalTo("Germany") );

        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("FR") );
        assertThat( ctx.get("Country"), equalTo("France") );

        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("ES") );
        assertThat( ctx.get("Country"), equalTo("Spain") );

        assertThat(ctxIt.hasNext(), is(false));

    }

}