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

  @Test
  void first() throws IOException {
    Model model = CreatePom.of("g:a:1.0").toModel();
    Path target = Path.of("target", "pom-first.xml");

    Writer.to(model, target);

    String content = Files.lines(target).collect(Collectors.joining());
    String expected = Files.lines(Path.of("src", "test", "resources", "pom-first.xml")).collect(Collectors.joining());

    Diff build = DiffBuilder.compare(content).ignoreComments().ignoreWhitespace().withTest(expected).build();
    assertThat(build.getDifferences()).isEmpty();
  }

  @Test
  void second() throws IOException {
    Model model = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .toModel();
    Path target = Path.of("target", "pom-second.xml");

    Writer.to(model, target);

    String content = Files.lines(target).collect(Collectors.joining());
    String expected = Files.lines(Path.of("src", "test", "resources", "pom-second.xml")).collect(Collectors.joining());

    Diff build = DiffBuilder.compare(content).ignoreComments().ignoreWhitespace().withTest(expected).build();
    assertThat(build.getDifferences()).isEmpty();
  }

  @Test
  void third() throws IOException {
    Model model = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .modules("f1")
        .toModel();
    Path target = Path.of("target", "pom-third.xml");

    Writer.to(model, target);

    String content = Files.lines(target).collect(Collectors.joining());
    String expected = Files.lines(Path.of("src", "test", "resources", "pom-third.xml")).collect(Collectors.joining());

    Diff build = DiffBuilder.compare(content).ignoreComments().ignoreWhitespace().withTest(expected).build();
    assertThat(build.getDifferences()).isEmpty();
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
