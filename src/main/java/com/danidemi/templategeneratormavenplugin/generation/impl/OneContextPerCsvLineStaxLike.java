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

import com.danidemi.templategeneratormavenplugin.generation.ContextCreator;
import com.danidemi.templategeneratormavenplugin.generation.RowSource;
import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.ContextModelBuilder;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import com.danidemi.templategeneratormavenplugin.utils.TransformIteratorAdapter;

import java.util.Iterator;
import java.util.function.Function;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.validateArgumentNotNull;

/**
 * Creates one context for each line in a CSV.
 * This implementation does not need to load the whole CSV in memory but for that reasons some meta data are not available.
 */
public class OneContextPerCsvLineStaxLike implements ContextCreator {

    private final RowSource rowSource;
    private final ContextModelBuilder prototype;

    public OneContextPerCsvLineStaxLike(RowSource rowSource, ContextModelBuilder prototype) {

        this.rowSource = validateArgumentNotNull(rowSource);
        this.prototype = validateArgumentNotNull(prototype);
    }

    @Override public Iterable<ContextModel> contexts() {

        final Function<IRowModel, ContextModel> mapping = new Function<IRowModel, ContextModel>() {
            @Override public ContextModel apply(IRowModel iRowModel) {
                return ((ContextModelBuilder)prototype.clone())
                        .add(iRowModel)
                        .build();
            }
        };

        return new Iterable<ContextModel>(){

            @Override public Iterator<ContextModel> iterator() {
                return new TransformIteratorAdapter<IRowModel, ContextModel>(
                        rowSource.iterator(),
                        mapping
                );
            }
        };
    }

}
