package com.danidemi.templategeneratormavenplugin.maven;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.codehaus.plexus.PlexusTestCase.getTestFile;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GenerateTest {

    @Rule MojoRule mojoRule = new MojoRule();

    @Test public void aTest() throws Exception {
        File pom = getTestFile( "C:\\Users\\danidemi\\workspace\\repos\\danidemi\\template-generator-maven-plugin\\src\\main\\resources\\pom.xml" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        Generate myMojo = (Generate) mojoRule.lookupMojo( "touch", pom );
        assertNotNull( myMojo );
        myMojo.execute();
    }

}