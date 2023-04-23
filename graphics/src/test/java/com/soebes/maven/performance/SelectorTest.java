package com.soebes.maven.performance;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

class SelectorTest {

  @Test
  void selectAllFilesInJSONDirectory() throws IOException {
    Path basePath = Path.of("json");

    var paths = Selector.selectAllFiles(basePath);
    paths.forEach(s -> System.out.println("s = " + s));
  }

  @Test
  void selectOnlyJsonFiles() throws IOException {
    Path basePath = Path.of("json");

    var jsonFilesOnly = Selector.selectJsonFilesOnly(basePath);
    jsonFilesOnly.forEach(s -> System.out.println("s = " + s));
  }


}