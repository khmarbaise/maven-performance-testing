package com.soebes.maven.performance;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.soebes.maven.performance.maven.Dependency;
import com.soebes.maven.performance.maven.Plugin;
import com.soebes.maven.performance.maven.Property;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO: Find a way to fail the tests in case of the throwing an Exception in verify method. And remove the usage of
 * the IOException from all test methods.
 *
 * @author Karl Heinz Marbaise
 */
class CreatePomTest {

  private static final Plugin MAVEN_COMPILER_PLUGIN = Plugin.of("org.apache.maven.plugins", "maven-compiler-plugin", "3.8.1");
  private static final Plugin VERSION_MAVEN_PLUGIN = Plugin.of("org.mojohaus.mojo", "versions-maven-plugin", "2.8.1");

  private static final Path EXPECTED_POMS = Path.of("src", "test", "resources", "expected", "poms");

  void verify(PomBuilder createPom, String pomFile) throws IOException {
    Path target = Path.of("target", pomFile);

    Writer.to(createPom.toModel(), target);

    String content = Files.lines(target).collect(Collectors.joining());
    String expected = Files.lines(EXPECTED_POMS.resolve(pomFile)).collect(Collectors.joining());

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
  void seventh_one() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencyManagement(new Dependency("dpm", "dpma", "dpmv"))
        .dependencies(new Dependency("dg", "da"))
        .build()
        .pluginManagement(MAVEN_COMPILER_PLUGIN)
        .modules("f1");
    verify(createPom, "pom-seventh_a.xml");
  }

  @Test
  void eighth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencyManagement(new Dependency("dpm", "dpma", "dpmv"))
        .dependencies(new Dependency("dg", "da"))
        .build()
        .pluginManagement(List.of(MAVEN_COMPILER_PLUGIN))
        .plugins(VERSION_MAVEN_PLUGIN)
        .modules("f1");
    verify(createPom, "pom-eighth.xml");
  }

  @Test
  void ninth() throws IOException {
    CreatePom createPom = CreatePom.of("g:a:1.0", "war")
        .parent("p:a:2.0")
        .properties(new Property("maven.compiler.source", "7"), new Property("maven.compiler.target", "7"))
        .dependencyManagement(new Dependency("dpm", "dpma", "dpmv"))
        .dependencies(new Dependency("dg", "da"))
        .build()
        .pluginManagement(List.of(MAVEN_COMPILER_PLUGIN))
        .plugins(VERSION_MAVEN_PLUGIN)

        .modules("f1");
    verify(createPom, "pom-ninth.xml");
  }

  @Test
  void final_test() throws IOException {
    var pomOne = CreatePom.of("g", "a", "1.0")
        .parent("g:a:2.0")
        .dependencies(new Dependency("junit", "junit", "4.12.3"))
        .build()
        .pluginManagement(List.of(MAVEN_COMPILER_PLUGIN));

    verify(pomOne, "pom-final.xml");
  }

  @Test
  void packgingJar() throws IOException {
    var pomOne = CreatePom.of("g", "a", "1.0")
        .packaging(CreatePom.Packaging.jar)
        .parent("g:a:2.0")
        .dependencies(new Dependency("junit", "junit", "4.12.3"))
        .build()
        .pluginManagement(List.of(MAVEN_COMPILER_PLUGIN));

    verify(pomOne, "pom-packaging-jar.xml");
  }

  @Test
  void packgingEAR() throws IOException {
    var pomOne = CreatePom.of("g", "a", "1.0")
        .packaging(CreatePom.Packaging.ear)
        .parent("g:a:2.0")
        .dependencies(new Dependency("junit", "junit", "4.12.3"))
        .build()
        .pluginManagement(List.of(MAVEN_COMPILER_PLUGIN));

    verify(pomOne, "pom-packaging-ear.xml");
  }

}
