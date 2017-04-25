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

import com.danidemi.templategeneratormavenplugin.generation.impl.CsvRowSource;
import com.danidemi.templategeneratormavenplugin.generation.impl.OneContextPerCsvFile;
import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.Iterator;

import static com.danidemi.templategeneratormavenplugin.TestUtils.mockPrototype;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class OneContextPerCsvFileTest {

    @Test(expected = IllegalArgumentException.class) public void failWhenSourceDoesNotExist() {
        new OneContextPerCsvFile(null, mockPrototype());
    }

    @Test public void produceContextFromAnotherCsvFile() {

        CsvRowSource rowSource = new CsvRowSource(new InputStreamReader(Object.class.getResourceAsStream("/codeAndCurrency.csv")));

        OneContextPerCsvFile sut = new OneContextPerCsvFile(rowSource, mockPrototype());

        Iterator<ContextModel> ctxIt = sut.contexts().iterator();

        ContextModel ctx;
        ctx = ctxIt.next();

        assertThat( ctx.getMeta().getCount().getRows(), equalTo(3) );
        assertThat( ctx.getMeta().getCount().getLastIndex(), equalTo(2) );

        Iterator<IRowModel> rows = ctx.rowIterator().iterator();

        IRowModel rowCtx = rows.next();
        assertThat( rowCtx.getMeta().getIndex(),      equalTo(0L) );
        assertThat( rowCtx.getMeta().getCount(),      equalTo(1L) );
        assertThat( rowCtx.getData().get("Code"),     equalTo("EUR") );
        assertThat( rowCtx.getData().get("Currency"), equalTo("Euro") );

        rowCtx = rows.next();
        assertThat( rowCtx.getMeta().getIndex(),      equalTo(1L) );
        assertThat( rowCtx.getMeta().getCount(),      equalTo(2L) );
        assertThat( rowCtx.getData().get("Code"),     equalTo("USD") );
        assertThat( rowCtx.getData().get("Currency"), equalTo("Dollar") );

        rowCtx = rows.next();
        assertThat( rowCtx.getMeta().getIndex(),      equalTo(2L) );
        assertThat( rowCtx.getMeta().getCount(),      equalTo(3L) );
        assertThat( rowCtx.getData().get("Code"),     equalTo("GBP") );
        assertThat( rowCtx.getData().get("Currency"), equalTo("Pound") );

        assertThat(ctxIt.hasNext(), is(false));

    }



}
