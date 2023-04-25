package com.soebes.maven.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

class Selector {
  private static final Predicate<Path> IS_REGULAR_FILE = Files::isRegularFile;
  private static final Predicate<Path> IS_READABLE = Files::isReadable;
  private static final Predicate<Path> IS_VALID_FILE = IS_REGULAR_FILE.and(IS_READABLE);

  static List<Path> selectAllFiles(Path start) throws IOException {
    try (var pathStream = Files.walk(start, 1)) {
      return pathStream.filter(IS_VALID_FILE).toList();
    }
  }

  static List<Path> selectJsonFilesOnly(Path start) throws IOException {
    var paths = selectAllFiles(start);
    return  paths.stream()
        .filter(s -> s.getFileName().toString().endsWith(".json"))
        .toList();
  }
}
