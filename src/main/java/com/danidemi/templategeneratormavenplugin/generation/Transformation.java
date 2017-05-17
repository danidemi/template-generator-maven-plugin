package com.danidemi.templategeneratormavenplugin.generation;

public interface Transformation<Original, Transformed> {

    Transformed apply(Original original);

}
