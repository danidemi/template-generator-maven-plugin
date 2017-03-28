package com.danidemi.templategeneratormavenplugin.maven;

import com.danidemi.templategeneratormavenplugin.generation.IncludeAllRowFilter;
import com.danidemi.templategeneratormavenplugin.generation.OneContextPerTag;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OneContextPerTagTest {

    @Test
    public void shouldGroupRowsByTags() {

        // given
        File csv = new File(getClass().getResource("/organizational.csv").getFile());

        OneContextPerTag sut = new OneContextPerTag(csv.getAbsolutePath(), new IncludeAllRowFilter());
        sut.addTagExpression("@{Head}");
        sut.addTagExpression("@{Worker}");

        // when
        Iterator<Map<String, Object>> ctxs = sut.iterator();

        // then
        {
            Map<String, Object> ctx = ctxs.next();
            List<Map<String, Object>> rows = (List<Map<String, Object>>) ctx.get("rows");
            assertRow(rows, 0, "HQ", "Dafne Nuvolari", "Ashley Delarosa");
            assertRow(rows, 1, "HQ", "Dafne Nuvolari", "Ciara Wright");
            assertRow(rows, 2, "HQ", "Dafne Nuvolari", "Ganda Ramasamy");
            assertEquals(rows.size(), 3);
        }
        {
            Map<String, Object> ctx = ctxs.next();
            List<Map<String, Object>> rows = (List<Map<String, Object>>) ctx.get("rows");
            assertRow(rows, 0, "HQ", "Dafne Nuvolari", "Ciara Wright");
            assertRow(rows, 1, "HQ", "Ciara Wright", "Sumiko Hiratasuka");
            assertRow(rows, 2, "HQ", "Ciara Wright", "Adela Dvorak");
            assertEquals(rows.size(), 3);
        }
        {
            Map<String, Object> ctx = ctxs.next();
            List<Map<String, Object>> rows = (List<Map<String, Object>>) ctx.get("rows");
            assertRow(rows, 2, "HQ", "Dafne Nuvolari", "Ganda Ramasamy");
            assertRow(rows, 1, "Sale", "Ganda Ramasamy", "Sezen Muhtar");
            assertRow(rows, 2, "Sale", "Ganda Ramasamy", "Gabriela Telles Salgado");
            assertEquals(rows.size(), 3);
        }
        {
            Map<String, Object> ctx = ctxs.next();
            List<Map<String, Object>> rows = (List<Map<String, Object>>) ctx.get("rows");
            assertRow(rows, 2, "Sale", "Ganda Ramasamy", "Gabriela Telles Salgado");
            assertRow(rows, 1, "Sale", "Gabriela Telles Salgado", "Ida Pettersson");
            assertEquals(rows.size(), 2);
        }


    }

    private void assertRow(List<Map<String, Object>> rows, int index, String expectedDepartment, String expectedHead, String expectedSubordinate) {
        assertEquals(expectedDepartment, rows.get(index).get("Department") );
        assertEquals(expectedHead, rows.get(index).get("Head") );
        assertEquals(expectedSubordinate, rows.get(index).get("Worker") );
    }

}