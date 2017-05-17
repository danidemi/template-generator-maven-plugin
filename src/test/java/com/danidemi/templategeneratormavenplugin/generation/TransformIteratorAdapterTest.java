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

import com.danidemi.templategeneratormavenplugin.utils.TransformIteratorAdapter;
import com.google.common.base.Function;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;


public class TransformIteratorAdapterTest {

    @Test
    public void shouldThrow() {

        Iterator<String> planets = asList("Earth", "Mars", "Neptune").iterator();

        TransformIteratorAdapter<String, Integer> sut = new TransformIteratorAdapter<>(planets, new Function<String, Integer>() {
            @Override
            public Integer apply(String p) {
                return p.length();
            }
        });

        assertTrue( sut.hasNext() );
        assertEquals( 5, sut.next().intValue() );

        assertTrue( sut.hasNext() );
        assertEquals( 4, sut.next().intValue() );

        assertTrue( sut.hasNext() );
        assertEquals( 7, sut.next().intValue() );

        assertFalse( sut.hasNext() );
        try{
            sut.next();
            fail("NoSuchElementException expected");
        }catch(NoSuchElementException nsee){
            // ok
        }
    }

}
