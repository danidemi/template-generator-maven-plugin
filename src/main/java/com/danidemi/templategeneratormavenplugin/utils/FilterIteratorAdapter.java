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


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilterIteratorAdapter<E> implements Iterator<E> {

    private final Predicate<E> filter;
    private Iterator<E> iterator;

    private E next = null;
    private E ahead = null;
    private boolean aheasSet = false;
    private boolean hasNextBeenRetrieved = false;

    public FilterIteratorAdapter(Iterator<E> iterator, java.util.function.Predicate<E> filter) {
        this.iterator = iterator;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        if(!aheasSet){
            try{
                ahead = next();
                aheasSet = true;
                return true;
            }catch(NoSuchElementException nsee){
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public E next() {

        if(aheasSet){
            aheasSet = false;
            next = ahead;
            ahead = null;
        }else{
            boolean done = false;
            do {
                next = iterator.next();
                if( filter.test(next) ) {
                    done = true;
                }
            }while(done == false);
        }
        return next;

    }
}
