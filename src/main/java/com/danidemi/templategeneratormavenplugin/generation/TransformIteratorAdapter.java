package com.danidemi.templategeneratormavenplugin.generation;

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

import java.util.Iterator;
import java.util.function.Function;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.validateArgumentNotNull;

/**
 * Make a new {@link Iterator} out of an existing one, transforming all its element using a provided transformation function.
 */
class TransformIteratorAdapter<Original, Tranformed> implements Iterator<Tranformed> {

    private final Iterator<Original> iterator;
    private final Function<Original, Tranformed> mappingFunction;

    TransformIteratorAdapter(Iterator<Original> iterator, Function<Original, Tranformed> mappingFunction) {
        this.iterator = validateArgumentNotNull(iterator);
        this.mappingFunction = validateArgumentNotNull(mappingFunction);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Tranformed next() {
        return mappingFunction.apply(iterator.next());
    }
}
