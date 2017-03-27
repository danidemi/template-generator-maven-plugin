package com.danidemi.templategeneratormavenplugin.generation;


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
