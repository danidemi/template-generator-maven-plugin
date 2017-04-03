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
