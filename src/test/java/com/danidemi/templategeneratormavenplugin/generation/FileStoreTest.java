package com.danidemi.templategeneratormavenplugin.generation;

import org.codehaus.plexus.util.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileStoreTest {

    @Rule public TemporaryFolder tmp = new TemporaryFolder();
    @Rule public TestName testName = new TestName();

    @Test public void storeFileWithPathInName() throws Exception {

        // given
        File folderForTest = new File(tmp.getRoot(), testName.getMethodName());
        folderForTest.mkdirs();

        FileStore sut = new FileStore(folderForTest);

        StringWriter s = new StringWriter();
        s.append("Hello Aliens.");
        String fileName = "this/is/a/package/out.java";

        // when
        sut.storeContentToFile(s, fileName);

        // then
        File root = new File(folderForTest, "this/is/a/package");
        List<File> list = Arrays.asList(root.listFiles());

        assertThat( String.valueOf(list), list.size(), is(1) );
        String s1 = FileUtils.fileRead(list.get(0));
        assertThat( s1, equalTo("Hello Aliens.") );

    }

    @Test public void storeFileWithSimpleName() throws Exception {

        // given
        File folderForTest = new File(tmp.getRoot(), testName.getMethodName());
        folderForTest.mkdirs();

        FileStore sut = new FileStore(folderForTest);

        StringWriter s = new StringWriter();
        s.append("Hello World.");
        String fileName = "thepath." + (int) (Math.random() * 100) + ".java";

        // when
        sut.storeContentToFile(s, fileName);

        // then
        File[] list = folderForTest.listFiles();
        assertThat( list.length, is(1) );
        String s1 = FileUtils.fileRead(list[0]);
        assertThat( s1, equalTo("Hello World.") );

    }

}