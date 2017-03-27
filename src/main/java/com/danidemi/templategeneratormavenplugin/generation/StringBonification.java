package com.danidemi.templategeneratormavenplugin.generation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringBonification {

    public static void main(String[] args) {
        System.out.println( new StringBonification().bonify("@{row.BTBGClassName.startsWith(\"BG0000\") || row.parentClass.startWith(\"BG0000\")}") );
    }

    private String bonify(String s) {
        //s.replaceAll("@\\{", "${");
        //return Pattern.compile("@\\{").matcher(s).replaceAll(Matcher.quoteReplacement( "${" ));
        return s.replace("@{", "${");
    }

}
