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
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class OneContextPerCsvFileTest {

    @Test(expected = IllegalArgumentException.class) public void failWhenSourceDoesNotExist() {
        OneContextPerCsvFile sut = OneContextPerCsvFile.fromClasspath("/does-not-exists");
    }

    @Test public void produceContextFromAnotherCsvFile() {

        OneContextPerCsvFile sut = OneContextPerCsvFile.fromClasspath("/codeAndCurrency.csv");

        Iterator<ContextModel> ctxIt = sut.contexts().iterator();

        ContextModel ctx;
        ctx = ctxIt.next();

        assertThat( ctx.getMeta().getCount().getRows(), equalTo(3) );
        assertThat( ctx.getMeta().getCount().getLastIndex(), equalTo(2) );


        List< Map<String, Object> > rows = (List<Map<String, Object>>) ctx.rowIterator().iterator();

        Map<String, Object> rowCtx = rows.get(0);
        assertThat( rowCtx.get("RowIndex"), equalTo(0) );
        assertThat( rowCtx.get("RowCount"), equalTo(1) );
        assertThat( rowCtx.get("Code"), equalTo("EUR") );
        assertThat( rowCtx.get("Currency"), equalTo("Euro") );

        rowCtx = rows.get(1);
        assertThat( rowCtx.get("RowIndex"), equalTo(1) );
        assertThat( rowCtx.get("RowCount"), equalTo(2) );
        assertThat( rowCtx.get("Code"), equalTo("USD") );
        assertThat( rowCtx.get("Currency"), equalTo("Dollar") );

        rowCtx = rows.get(2);
        assertThat( rowCtx.get("RowIndex"), equalTo(2) );
        assertThat( rowCtx.get("RowCount"), equalTo(3) );
        assertThat( rowCtx.get("Code"), equalTo("GBP") );
        assertThat( rowCtx.get("Currency"), equalTo("Pound") );

        assertThat(ctxIt.hasNext(), is(false));

    }

}
