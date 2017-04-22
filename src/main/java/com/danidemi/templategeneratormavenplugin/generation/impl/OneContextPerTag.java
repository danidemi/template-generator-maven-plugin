package com.danidemi.templategeneratormavenplugin.generation.impl;

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

import com.danidemi.templategeneratormavenplugin.generation.ContextCreator;
import com.danidemi.templategeneratormavenplugin.generation.RowSource;
import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.ContextModelBuilder;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;

import java.util.*;

public class OneContextPerTag implements ContextCreator {

    private final List<String> tagExpressions;
    private final RowSource rowSource;

    public OneContextPerTag(List<String> tagExpressions, RowSource rowSource) {
        this.tagExpressions = tagExpressions;
        this.rowSource = rowSource;
    }

    @Override public Iterable<ContextModel> contexts() {

        Map<String, ContextModelBuilder> tag2context = new LinkedHashMap<>();

        for (IRowModel iRowModel : rowSource) {

            Set<String> tagsForRow = new HashSet<>();
            for (String tagExpression : tagExpressions) {
                tagExpression = tagExpression.replace("@{", "${");
                String tag = new JuelEval<String>().invoke(tagExpression, "row", iRowModel);
                tagsForRow.add(tag);
            }

            for (String tag : tagsForRow) {

                ContextModelBuilder builder = tag2context.get(tag);

                builder.add(iRowModel);

            }

        }

        List<ContextModel> contexts = new ArrayList<>();
        for (ContextModelBuilder builder : tag2context.values()) {
            contexts.add( builder.build() );
        }

        return contexts;

    }

    public void addTagExpression(String expression) {
        this.tagExpressions.add(expression);
    }

}
