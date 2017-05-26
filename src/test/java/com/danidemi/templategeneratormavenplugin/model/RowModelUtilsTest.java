package com.danidemi.templategeneratormavenplugin.model;

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

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RowModelUtilsTest {

    @Test
    public void shouldDescribe() throws Exception {

        // given
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("k1", "v1");
        data.put("k2", "v2");

        RowMetaModel meta = new RowMetaModel(100, 120);
        InMemoryRowModel rowModel = new InMemoryRowModel(data, meta);

        // when
        String describe = RowModelUtils.describe(rowModel);

        // then
        assertEquals( "{k1=v1, k2=v2 | count=100, sourceCount=120}", describe );

    }

}
