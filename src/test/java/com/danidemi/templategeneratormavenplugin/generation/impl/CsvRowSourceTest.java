package com.danidemi.templategeneratormavenplugin.generation.impl;

/*-
 * #%L
 * template-generator-maven-plugin
$Id:$
$HeadURL:$
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
