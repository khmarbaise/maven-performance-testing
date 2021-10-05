package com.soebes.maven.performance;

import org.apache.maven.model.Model;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class WriteTest {

  void check(CreatePom createPom, String pomFile) throws IOException {
    Path target = Path.of("target", pomFile);

    Writer.to(createPom.toModel(), target);

    String content = Files.lines(target).collect(Collectors.joining());
    String expected = Files.lines(Path.of("src", "test", "resources", pomFile)).collect(Collectors.joining());

    Diff build = DiffBuilder.compare(content).ignoreComments().ignoreWhitespace().withTest(expected).build();
    assertThat(build.getDifferences()).isEmpty();
  }

  @Test
  void first() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0");
    check(createPom, "pom-first.xml");
  }

  @Test
  void second() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0");
    Path target = Path.of("target", "pom-second.xml");
    check(createPom, "pom-second.xml");
  }

  @Test
  void third() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .modules("f1");
    check(createPom, "pom-third.xml");
  }

  @Test
  void forth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .modules("f1");
    check(createPom, "pom-forth.xml");
  }

  @Test
  void fifth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencies(new Dependency("dg", "da", "dv"))
        .modules("f1");
    check(createPom, "pom-fifth.xml");
  }

  @Test
  @Disabled
  void finalxxx() {
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


    Path target = Path.of("target", "pom-second.xml");

    Writer.to(pomOne, target);
  }

}
