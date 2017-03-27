package com.danidemi.templategeneratormavenplugin.generation;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;


public class TransformIteratorAdapterTest {

    @Test
    public void shouldThrow() {

        Iterator<String> planets = asList("Earth", "Mars", "Neptune").iterator();

        TransformIteratorAdapter<String, Integer> sut = new TransformIteratorAdapter<>(planets, p -> p.length());

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