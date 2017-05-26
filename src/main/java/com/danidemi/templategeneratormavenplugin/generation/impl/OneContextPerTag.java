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
import com.danidemi.templategeneratormavenplugin.model.RowModelUtils;

import javax.el.ELException;
import java.util.*;

public class OneContextPerTag implements ContextCreator {

    private final List<String> tagExpressions;
    private final RowSource rowSource;
    private final ContextModelBuilder prototype;

    public OneContextPerTag(List<String> tagExpressions, RowSource rowSource, ContextModelBuilder prototype) {
        this.tagExpressions = new ArrayList<>( tagExpressions );
        this.rowSource = rowSource;
        this.prototype = prototype;
    }

    @Override public Iterable<ContextModel> contexts() {

        Map<String, ContextModelBuilder> tag2context = new LinkedHashMap<>();

        for (IRowModel iRowModel : rowSource) {

            Set<String> tagsForRow = new LinkedHashSet<>();
            for (String tagExpression : tagExpressions) {
                try {
                    tagExpression = tagExpression.replace("@{", "${");
                    String tag = new JuelEval<String>().invoke(tagExpression, "row", iRowModel);
                    tagsForRow.add(tag);

//                    System.out.println("===========================");
//                    System.out.println(tagExpression);
//                    System.out.println(RowModelUtils.describe( iRowModel ));
//                    System.out.println("===========================");
                }catch (ELException pnfe){
                    throw new RuntimeException(
                            String.format( "An error occurred while executing expression '%s' in following model:\n=====================\n%s\n=====================\nOriginal exception follows.", tagExpression, RowModelUtils.describe( iRowModel ) ),
                            pnfe);
                }


            }

            for (String tag : tagsForRow) {

                if(!tag2context.containsKey(tag)){
                    tag2context.put(tag, (ContextModelBuilder)prototype.clone());
                }
                ContextModelBuilder builder = tag2context.get(tag);
                builder.add(iRowModel);

            }

        }

        List<ContextModel> contexts = new ArrayList<>();
        for (Map.Entry<String, ContextModelBuilder> contextEntry : tag2context.entrySet()) {
            contexts.add( contextEntry.getValue().build() );
        }

        return contexts;

    }

    public void addTagExpression(String expression) {
        this.tagExpressions.add(expression);
    }

}
