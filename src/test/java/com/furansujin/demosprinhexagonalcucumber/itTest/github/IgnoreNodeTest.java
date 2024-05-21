package com.furansujin.demosprinhexagonalcucumber.itTest.github;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.utils.IgnoreNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;  // Import the File class
import java.nio.file.*;
import java.util.Comparator;

import static org.junit.Assert.*;

public class IgnoreNodeTest {

    private Path testDirectory;

    @Before
    public void setUp() throws IOException {
        testDirectory = Files.createTempDirectory("ignoreTest");
        Files.createFile(testDirectory.resolve("test.txt"));
        Files.createFile(testDirectory.resolve("example.java"));
        Files.createDirectory(testDirectory.resolve("logs"));
        Files.createFile(testDirectory.resolve("logs/log.txt"));
    }

    @After
    public void tearDown() throws IOException {
        Files.walk(testDirectory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testIgnorePatterns() throws IOException {
        String ignoreContent = "*.txt\nlogs/**";
        IgnoreNode ignoreNode = new IgnoreNode();
        ignoreNode.parse(new ByteArrayInputStream(ignoreContent.getBytes()));

        assertFalse("File should not be ignored", ignoreNode.isIgnored(testDirectory.resolve("example.java"), testDirectory));
        assertTrue("TXT files should be ignored", ignoreNode.isIgnored(testDirectory.resolve("test.txt"), testDirectory));
        assertTrue("Files in logs directory should be ignored", ignoreNode.isIgnored(testDirectory.resolve("logs/log.txt"), testDirectory));
    }
}
