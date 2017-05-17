package com.danidemi.templategeneratormavenplugin.generation;

/*-
 * #%L
 * template-generator-maven-plugin
 * $Id:$
 * $HeadURL:$
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

import com.danidemi.templategeneratormavenplugin.TestUtils;
import com.danidemi.templategeneratormavenplugin.generation.impl.CsvRowSource;
import com.danidemi.templategeneratormavenplugin.generation.impl.OneContextPerTag;
import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import org.junit.Test;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OneContextPerTagTest {

    @Test
    public void shouldGroupRowsByTags() {

        // given
        File csv = new File(getClass().getResource("/organizational.csv").getFile());

        OneContextPerTag sut = new OneContextPerTag(Arrays.asList("@{row.data.Head}", "@{row.data.Worker}"),
                new CsvRowSource(
                        new InputStreamReader( Object.class.getResourceAsStream("/organizational.csv") ) ), TestUtils.mockPrototype());



        // when
        Iterator<ContextModel> ctxs = sut.contexts().iterator();

        // then
        {
            // all rows about Dafne Nuvolari
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "HQ", "Dafne Nuvolari", "Ashley Delarosa");
            assertRow(rows, 1, "HQ", "Dafne Nuvolari", "Ciara Wright");
            assertRow(rows, 2, "HQ", "Dafne Nuvolari", "Ganda Ramasamy");
            assertEquals( rows.toString(),3, rows.size());
        }
        {
            // all rows about Ashley Delarosa
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "HQ", "Dafne Nuvolari", "Ashley Delarosa");
            assertRow(rows, 1, "Account", "Ashley Delarosa", "San Hye-young");
            assertRow(rows, 2, "Account", "Ashley Delarosa", "Nabila Fahim");
            assertEquals( rows.toString(),3, rows.size());
        }
        {
            // all rows about Ciara Wright
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "HQ", "Dafne Nuvolari", "Ciara Wright");
            assertRow(rows, 1, "IT", "Ciara Wright", "Sumiko Hiratasuka");
            assertRow(rows, 2, "IT", "Ciara Wright", "Adela Dvorak");
            assertEquals(3, rows.size());
        }
        {
            // all rows about Ganda Ramasamy
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "HQ", "Dafne Nuvolari", "Ganda Ramasamy");
            assertRow(rows, 1, "Sale", "Ganda Ramasamy", "Sezen Muhtar");
            assertRow(rows, 2, "Sale", "Ganda Ramasamy", "Gabriela Telles Salgado");
            assertEquals(3, rows.size());
        }
        {
            // all rows about Sumiko Hiratasuka
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "IT", "Ciara Wright", "Sumiko Hiratasuka");
            assertEquals(1, rows.size());
        }
        {
            // all rows about Adela Dvorak
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "IT", "Ciara Wright", "Adela Dvorak");
            assertEquals(1, rows.size());
        }
        {
            // all rows about San Hye-young
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "Account", "Ashley Delarosa", "San Hye-young");
            assertEquals(1, rows.size());
        }
        {
            // all rows about Nabila Fahim
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "Account", "Ashley Delarosa", "Nabila Fahim");
            assertEquals(1, rows.size());
        }
        {
            // all rows about Nabila Fahim
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "Sale", "Ganda Ramasamy", "Sezen Muhtar");
            assertEquals(1, rows.size());
        }
        {
            // all rows about Gabriela Telles Salgado
            ContextModel ctx = ctxs.next();
            List<IRowModel> rows = ctx.getRows();
            assertRow(rows, 0, "Sale", "Ganda Ramasamy", "Gabriela Telles Salgado");
            assertRow(rows, 1, "Sale", "Gabriela Telles Salgado", "Ida Pettersson");
            assertEquals(2, rows.size());
        }


    }

    private void assertRow(List<IRowModel> rows, int index, String expectedDepartment, String expectedHead, String expectedSubordinate) {
        assertNotNull(rows);
        assertNotNull(expectedDepartment);
        assertNotNull(expectedHead);
        assertNotNull(expectedSubordinate);

        IRowModel theRow = rows.get(index);
        assertEquals(theRow.toString(), expectedHead, theRow.getData().get("Head") );
        assertEquals(theRow.toString(), expectedSubordinate, theRow.getData().get("Worker") );
        assertEquals(theRow.toString(), expectedDepartment, theRow.getData().get("Department") );
    }

}
