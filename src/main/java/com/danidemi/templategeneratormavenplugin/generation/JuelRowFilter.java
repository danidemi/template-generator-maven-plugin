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

public class JuelRowFilter implements RowFilter {

    private final String includeRowExpression;

    public JuelRowFilter(String includeRowExpression) {
        this.includeRowExpression = includeRowExpression;
    }

    @Override public boolean keep(RowModel row) {
        String includeRowExpression = this.includeRowExpression;
        Class<Boolean> aClass = Boolean.class;
        Boolean result = new JuelEval<Boolean>().invoke(row, includeRowExpression);
        return result;
    }

    @Override public boolean keep(ContextModel contextModel) {
        String includeRowExpression = this.includeRowExpression;
        Class<Boolean> aClass = Boolean.class;
        Boolean result = new JuelEval<Boolean>().invoke(contextModel, includeRowExpression);
        return result;
    }

}
