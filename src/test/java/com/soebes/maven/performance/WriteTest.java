package com.soebes.maven.performance;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

class WriteTest {

  void write(Model model) throws IOException {
    model.setModelVersion("4.0.0");

    MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();

    Path target = Path.of("target", "pom.xml");
    mavenXpp3Writer.write(new FileOutputStream(target.toFile()), model);
  }

  @Test
  void first() throws IOException {
    Model model = CreatePom.of("g:a:1.0").toModel();
    write(model);

    Stream<String> content = Files.lines(Path.of("target", "pom.xml"));
    Stream<String> expected = Files.lines(Path.of("src", "test", "resources", "first.pom"));

    DiffBuilder.compare(content).ignoreComments().ignoreWhitespace().withTest(expected);
  }

  @Test
  void name() throws IOException {
    Model model = new Model();

    model.setGroupId("com.soebes.maven.performance.first");
    model.setArtifactId("mini");
    model.setVersion("0.0.1-SNAPSHOT");

    Model pomOne = CreatePom
        .of("g", "a", "1.0")
        .parent("g:a:2.0")
        .properties()
        .dependencies()
        .dependencyManagement(List.of())
        .build()
        .pluginManagement(List.of(Plugin.of("org.apache.maven.plugins", "maven-compiler-plugin", "3.8.2")))
        .plugins(List.of())
        .modules()
        .toModel();


    write(model);
  }

}
