package com.soebes.maven.performance;

import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class WriteTest {

  private static final Plugin MAVEN_COMPILER_PLUGIN = Plugin.of("org.apache.maven.plugins", "maven-compiler-plugin", "3.8.1");
  private static final Plugin VERSION_MAVEN_PLUGIN = Plugin.of("org.mojohaus.mojo", "versions-maven-plugin", "2.8.1");

  void verify(CreatePom createPom, String pomFile) throws IOException {
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
    verify(createPom, "pom-first.xml");
  }

  @Test
  void second() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0");
    verify(createPom, "pom-second.xml");
  }

  @Test
  void third() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .modules("f1");
    verify(createPom, "pom-third.xml");
  }

  @Test
  void forth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .modules("f1");
    verify(createPom, "pom-forth.xml");
  }

  @Test
  void fifth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencies(new Dependency("dg", "da", "dv"))
        .modules("f1");
    verify(createPom, "pom-fifth.xml");
  }

  @Test
  void sixth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencyManagement(new Dependency("dpm", "dpma", "dpmv"))
        .dependencies(new Dependency("dg", "da"))
        .modules("f1");
    verify(createPom, "pom-sixth.xml");
  }

  @Test
  void seventh() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencyManagement(new Dependency("dpm", "dpma", "dpmv"))
        .dependencies(new Dependency("dg", "da"))
        .build()
        .pluginManagement(MAVEN_COMPILER_PLUGIN)
        .modules("f1");
    verify(createPom, "pom-seventh.xml");
  }

  @Test
  void eighth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencyManagement(new Dependency("dpm", "dpma", "dpmv"))
        .dependencies(new Dependency("dg", "da"))
        .build()
        .pluginManagement(MAVEN_COMPILER_PLUGIN)
        .plugins(VERSION_MAVEN_PLUGIN)
        .modules("f1");
    verify(createPom, "pom-eighth.xml");
  }

  @Test
  void final_test() throws IOException {
    CreatePom pomOne = CreatePom
        .of("g", "a", "1.0")
        .parent("g:a:2.0")
        .properties()
        .dependencies(new Dependency("junit", "junit", "4.12.3"))
        .build()
        .pluginManagement(MAVEN_COMPILER_PLUGIN)
        .plugins();

    verify(pomOne, "pom-final.xml");
  }

}
