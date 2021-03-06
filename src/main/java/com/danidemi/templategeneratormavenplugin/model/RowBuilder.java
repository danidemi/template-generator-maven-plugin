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

import java.util.Map;

/**
 * Stateful builder that ease the creation of {@link InMemoryRowModel rows} keeping track of their progressive count.
 */
class RowBuilder {

    private int rowCount;

    RowBuilder(){
        rowCount = 1;
    }

    public IRowModel nextRow(Map<String, Object> data, int sourceCount) {
        RowMetaModel meta = new RowMetaModel(rowCount++, sourceCount);
        return new InMemoryRowModel(data, meta);
    }

}
