package com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

public class IgnoreNode {
    private final List<PathMatcher> ignorePatterns;

    public IgnoreNode() {
        this.ignorePatterns = new ArrayList<>();
    }

    public void parse(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // Ignore empty lines and comments
                    ignorePatterns.add(FileSystems.getDefault().getPathMatcher("glob:" + line));
                }
            }
        }
    }

    public boolean isIgnored(Path file, Path rootPath) {
        Path relativePath = rootPath.relativize(file);
        return ignorePatterns.stream().anyMatch(pattern -> pattern.matches(relativePath));
    }
}
