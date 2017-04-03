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

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


public class FilterIteratorAdapterTest {

    @Test
    public void shouldFilter() {

        // given
        List<String> countries = asList("Italy/EU", "California/US", "France/EU", "Ohio/US", "Alberta/Canada");
        Iterator<String> iterator = countries.iterator();

        // when
        FilterIteratorAdapter<String> newIterator = new FilterIteratorAdapter<>(iterator, s -> s.contains("/EU"));

        // then
        assertTrue( newIterator.hasNext() );
        assertThat( newIterator.next(), equalTo("Italy/EU")  );

        assertTrue( newIterator.hasNext() );
        assertThat( newIterator.next(), equalTo("France/EU")  );

        assertFalse( newIterator.hasNext() );

    }

    @Test
    public void shouldRunTheFullSequenceOfNexts() {

        // given
        List<String> countries = asList("Italy/EU", "California/US", "France/EU", "Ohio/US", "Alberta/Canada");
        Iterator<String> iterator = countries.iterator();

        // when
        FilterIteratorAdapter<String> newIterator = new FilterIteratorAdapter<>(iterator, s -> s.contains("/EU"));

        // then
        assertThat( newIterator.next(), equalTo("Italy/EU")  );
        assertThat( newIterator.next(), equalTo("France/EU")  );
        try{
            newIterator.next();
            fail(NoSuchElementException.class.getSimpleName() + " expected, none thrown,");
        }catch(NoSuchElementException nsee){
            // ok! that's what we want.
        }

    }

}
