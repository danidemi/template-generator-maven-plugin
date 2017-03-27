package com.danidemi.templategeneratormavenplugin.generation;

/*-
 * #%L
 * template-generator-maven-plugin
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

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OneContextPerCsvLineTest {

    @Test(expected = IllegalArgumentException.class) public void failWhenSourceDoesNotExist() {
        OneContextPerCsvLine sut = OneContextPerCsvLine.fromClasspath("/does-not-exists", new IncludeAllRowFilter());
    }

    @Test public void produceContextFromFilteringInputRows() {

        OneContextPerCsvLine sut = OneContextPerCsvLine.fromClasspath("/codeAndCurrency.csv", row -> row.get("Code").equals("USD"));

        Iterator<Map<String, Object>> ctxIt = sut.iterator();

        Map<String, Object> ctx;
        ctx = ctxIt.next();
        assertThat( ctx.get("Code"), equalTo("USD") );
        assertThat( ctx.get("Currency"), equalTo("Dollar") );

        assertThat(ctxIt.hasNext(), is(false));

    }

    @Test public void produceContextFromAnotherCsvFile() {

        OneContextPerCsvLine sut = OneContextPerCsvLine.fromClasspath("/codeAndCurrency.csv", new IncludeAllRowFilter());

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

        OneContextPerCsvLine sut = OneContextPerCsvLine.fromClasspath("/codeAndCountry.csv", new IncludeAllRowFilter());

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
