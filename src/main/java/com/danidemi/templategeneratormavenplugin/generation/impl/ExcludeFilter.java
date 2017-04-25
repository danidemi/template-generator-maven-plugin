package com.danidemi.templategeneratormavenplugin.generation.impl;

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

import com.danidemi.templategeneratormavenplugin.generation.RowFilter;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;

public class ExcludeFilter implements RowFilter {

    private final RowFilter rowFilter;

    public ExcludeFilter(RowFilter rowFilter) {
        this.rowFilter = rowFilter;
    }

    @Override public boolean keep(IRowModel context) {
        return !rowFilter.keep(context);
    }

}