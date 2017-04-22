package com.danidemi.templategeneratormavenplugin.generation;

/*-
 * #%L
 * template-generator-maven-plugin
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
import com.danidemi.templategeneratormavenplugin.model.RowModel;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OneContextPerCsvLineStaxLikeTest {

    @Test(expected = IllegalArgumentException.class) public void failWhenSourceDoesNotExist() {
        OneContextPerCsvLineStaxLike sut = OneContextPerCsvLineStaxLike.fromClasspath("/does-not-exists", new IncludeAllRowFilter());
    }

    @Test public void produceContextFromFilteringInputRows() {

        RowFilter rowFilter = new RowFilter() {

            @Override public boolean keep(RowModel row) {
                return row.getData().get("Code").equals("USD");
            }

            @Override public boolean keep(ContextModel contextModel) {
                throw new RuntimeException("fail");
            }

            @Override public boolean keep(Map<String, Object> map) {
                throw new RuntimeException("fail");
            }
        };

        OneContextPerCsvLineStaxLike sut = OneContextPerCsvLineStaxLike.fromClasspath("/codeAndCurrency.csv",
                rowFilter
        );

        Iterator<RowModel> ctxIt = sut.contexts().iterator().next().rowIterator().iterator();

        RowModel ctx;
        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("USD") );
        assertThat( ctx.getData().get("Currency"), equalTo("Dollar") );

        assertThat(ctxIt.hasNext(), is(false));

    }

    @Test public void produceContextFromAnotherCsvFile() {

        OneContextPerCsvLineStaxLike sut = OneContextPerCsvLineStaxLike.fromClasspath("/codeAndCurrency.csv", new IncludeAllRowFilter());

        Iterator<RowModel> ctxIt = sut.contexts().iterator().next().rowIterator().iterator();

        RowModel ctx;
        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("EUR") );
        assertThat( ctx.getData().get("Currency"), equalTo("Euro") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("USD") );
        assertThat( ctx.getData().get("Currency"), equalTo("Dollar") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("GBP") );
        assertThat( ctx.getData().get("Currency"), equalTo("Pound") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        assertThat(ctxIt.hasNext(), is(false));

    }

    @Test public void produceAContextForEachRowInCsvFile() {

        OneContextPerCsvLineStaxLike sut = OneContextPerCsvLineStaxLike.fromClasspath("/codeAndCountry.csv", new IncludeAllRowFilter());

        Iterator<RowModel> ctxIt = sut.contexts().iterator().next().rowIterator().iterator();

        RowModel ctx;
        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("IT") );
        assertThat( ctx.getData().get("Country"), equalTo("Italy") );
        assertThat( ctx.getData().get("Continent"), equalTo("Europe") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("DE") );
        assertThat( ctx.getData().get("Country"), equalTo("Germany") );
        assertThat( ctx.getData().get("Continent"), equalTo("Europe") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("FR") );
        assertThat( ctx.getData().get("Country"), equalTo("France") );
        assertThat( ctx.getData().get("Continent"), equalTo("Europe") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("ES") );
        assertThat( ctx.getData().get("Country"), equalTo("Spain") );
        assertThat( ctx.getData().get("Continent"), equalTo("Europe") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("US") );
        assertThat( ctx.getData().get("Country"), equalTo("United States") );
        assertThat( ctx.getData().get("Continent"), equalTo("North America") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("EG") );
        assertThat( ctx.getData().get("Country"), equalTo("Egypt") );
        assertThat( ctx.getData().get("Continent"), equalTo("Africa") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("SA") );
        assertThat( ctx.getData().get("Country"), equalTo("South Africa") );
        assertThat( ctx.getData().get("Continent"), equalTo("Africa") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("CH") );
        assertThat( ctx.getData().get("Country"), equalTo("China") );
        assertThat( ctx.getData().get("Continent"), equalTo("Asia") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        ctx = ctxIt.next();
        assertThat( ctx.getData().get("Code"), equalTo("RU") );
        assertThat( ctx.getData().get("Country"), equalTo("Russia") );
        assertThat( ctx.getData().get("Continent"), equalTo("Asia") );
        assertThat( ctx.getMeta().getSourceIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getSourceCount(), equalTo(0) );
        assertThat( ctx.getMeta().getIndex(), equalTo(0) );
        assertThat( ctx.getMeta().getCount(), equalTo(0) );

        assertThat(ctxIt.hasNext(), is(false));

    }

}
