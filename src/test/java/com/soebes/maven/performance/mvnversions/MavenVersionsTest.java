package com.soebes.maven.performance.mvnversions;

import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;

class MavenVersionsTest {

  @Test
  void name() {
    Path rootTestDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
    Path downloadsDirectory = rootTestDirectory.resolve("downloads");
    var mavenVersions = new MavenVersions(downloadsDirectory);
    var paths = mavenVersions.listOfMavenVersions();

    var versions = paths.stream().map(Path::getFileName).map(s -> s.toString().substring(13)).toList();
    paths.forEach(s -> System.out.println("s = " + s.getFileName()));
    versions.forEach(s-> System.out.println("v = " + s));
  }
}