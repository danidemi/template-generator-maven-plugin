package com.danidemi.templategeneratormavenplugin.generation;

import org.codehaus.plexus.util.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileStoreTest {

    @Rule public TemporaryFolder tmp = new TemporaryFolder();

    @Test public void store() throws Exception {

        // given
        FileStore fileStore = new FileStore(tmp.getRoot());

        StringWriter s = new StringWriter();
        s.append("Hello World.");

        // when
        fileStore.store(s);

        // then
        File root = tmp.getRoot();
        File[] list = root.listFiles();
        assertThat( list.length, is(1) );
        String s1 = FileUtils.fileRead(list[0]);
        assertThat( s1, equalTo("Hello World.") );

    }

}