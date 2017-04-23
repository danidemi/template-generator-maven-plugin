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
import com.danidemi.templategeneratormavenplugin.model.IRowMetaModel;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import com.danidemi.templategeneratormavenplugin.utils.TransformIteratorAdapter;

import java.util.Iterator;
import java.util.Map;
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

                MyRowMeta delegateModel = new MyRowMeta(iRowModel.getMeta()){
                    @Override public long getIndex() {
                        return 0;
                    }

                    @Override public long getCount() {
                        return 1;
                    }
                };


                IRowModel myRowModel = new MyRowModel( iRowModel, delegateModel);
                return ((ContextModelBuilder)prototype.clone())
                        .add(myRowModel)
                        .build();
            }
        };

        Iterable<ContextModel> contextModels = new Iterable<ContextModel>() {

            @Override public Iterator<ContextModel> iterator() {
                Iterator<IRowModel> iterator = rowSource.iterator();
                return new TransformIteratorAdapter<IRowModel, ContextModel>(iterator, mapping);
            }
        };

        return contextModels;
    }

    private static class MyRowModel implements IRowModel {

        private final IRowModel delegate;
        private final IRowMetaModel delegateModel;

        private MyRowModel(IRowModel delegate, IRowMetaModel delegateModel) {
            this.delegate = delegate;
            this.delegateModel = delegateModel;
        }

        @Override public Map<String, Object> getData() {
            return delegate.getData();
        }

        @Override public IRowMetaModel getMeta() {
            return delegateModel;
        }
    }

    private static class MyRowMeta implements IRowMetaModel {

        private final IRowMetaModel delegate;

        private MyRowMeta(IRowMetaModel delegate) {
            this.delegate = delegate;
        }

        @Override public long getCount() {
            return delegate.getCount();
        }

        @Override public long getIndex() {
            return delegate.getIndex();
        }

        @Override public long getSourceIndex() {
            return delegate.getSourceIndex();
        }

        @Override public long getSourceCount() {
            return delegate.getSourceCount();
        }
    }


}
