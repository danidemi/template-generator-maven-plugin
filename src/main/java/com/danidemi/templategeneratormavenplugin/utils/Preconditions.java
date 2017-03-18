package com.danidemi.templategeneratormavenplugin.utils;

public class Preconditions {

    public static void checkState(boolean condition, String template, Object... theParam) {
        if(!condition) {
            throw new IllegalStateException(String.format(template, theParam));
        }
    }

    public static void checkArgument(boolean trueCondition, String template, Object... theParam) {
        if(!trueCondition) {
            throw new IllegalArgumentException(String.format(template, theParam));
        }
    }

    public static <T> T validateArgumentNotNull(T argument) {
        checkArgument(argument != null, "Null argument");
        return argument;
    }
}
