package com.danidemi.templategeneratormavenplugin.maven;

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

import com.danidemi.templategeneratormavenplugin.generation.impl.JuelRowFilter;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import com.danidemi.templategeneratormavenplugin.model.InMemoryRowModel;
import com.danidemi.templategeneratormavenplugin.model.RowMetaModel;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JuelRowFilterTest {

    @Test public void shouldDiscardContextNotSatyisfyingExpression() {

        JuelRowFilter sut = new JuelRowFilter("${row.data.a+row.data.a>10}");
        IRowModel context = newContextWithA(3);
        boolean discard = sut.keep(context);
        assertThat( discard, is(false) );

    }

    @Test public void shouldNotDiscardContextSatyisfyingExpression() {

        JuelRowFilter sut = new JuelRowFilter("${row.data.a<10}");
        IRowModel iRowModel = newContextWithA(5);
        boolean discard = sut.keep(iRowModel);
        assertThat( discard, is(true) );

    }

    private IRowModel newContextWithA(int a) {
        HashedMap context = new HashedMap();
        context.put("a", a);
        InMemoryRowModel rowModel = new InMemoryRowModel(context, new RowMetaModel(1, 2));
        return rowModel;
    }

}
