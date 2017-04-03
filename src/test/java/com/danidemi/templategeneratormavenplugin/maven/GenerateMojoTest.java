package com.danidemi.templategeneratormavenplugin.maven;

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

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GenerateMojoTest extends AbstractMojoTestCase {

    public void testOneContextPerRowWiltRowFilter() throws Exception {

        File pom = getTestFile( "src/test/resources/unit/filter-rows/pom.xml" );
        assertNotNull( pom );
        assertTrue( pom.exists() );


        GenerateMojo myMojo = (GenerateMojo) lookupMojo( "generate", pom );
        assertNotNull( myMojo );
        myMojo.execute();

        File outputDir = new File("target/output");
        List<File> files = asList( outputDir.listFiles() );
        assertThat( files.toString(), files.size(), is(1) );

        {
            String greetings = FileUtils.fileRead(new File(files.get(0), "FranceGreetings.txt"));
            assertThat(greetings, is("Greetings from France, in Europe."));
        }

        {
            String greetings = FileUtils.fileRead(new File(files.get(0), "GermanyGreetings.txt"));
            assertThat(greetings, is("Greetings from Germany, in Europe."));
        }

        {
            String greetings = FileUtils.fileRead(new File(files.get(0), "ItalyGreetings.txt"));
            assertThat(greetings, is("Greetings from Italy, in Europe."));
        }

        {
            String greetings = FileUtils.fileRead(new File(files.get(0), "SpainGreetings.txt"));
            assertThat(greetings, is("Greetings from Spain, in Europe."));
        }

    }

}
