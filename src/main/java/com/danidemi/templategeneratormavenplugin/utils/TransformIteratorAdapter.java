package com.danidemi.templategeneratormavenplugin.utils;

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

import com.google.common.base.Function;

import java.util.Iterator;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.validateArgumentNotNull;

/**
 * Make a new {@link Iterator} out of an existing one, transforming all its element using a provided transformation function.
 */
public class TransformIteratorAdapter<Original, Tranformed> implements Iterator<Tranformed> {

    private final Function<Original, Tranformed> mappingFunction;
    private final Iterator<Original> iterator;

    public TransformIteratorAdapter(Iterator<Original> iterator, Function<Original, Tranformed> mappingFunction) {
        this.iterator = iterator;
        this.mappingFunction = validateArgumentNotNull(mappingFunction);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Tranformed next() {
        Original next = iterator.next();
        return mappingFunction.apply(next);
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
