package com.soebes.maven.performance.scenario;

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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karl Heinz Marbaise
 */
class SetupMultiLevelScenarioTest {

  void verify(Path givenPom, Path expectedPom) throws IOException {
    String content = Files.lines(givenPom).collect(Collectors.joining());
    String expected = Files.lines(expectedPom).collect(Collectors.joining());

    Diff build = DiffBuilder.compare(content).ignoreComments().ignoreWhitespace().withTest(expected).build();
    assertThat(build.getDifferences()).isEmpty();
  }

  static void deleteDirectoryRecursively(Path startPath) {
    if (Files.notExists(startPath)) {
      return;
    }
    try (Stream<Path> streamOfPath = walk(startPath)) {
      List<File> collect = streamOfPath
          .sorted(reverseOrder())
          .map(Path::toFile)
          .collect(toList());
      collect
          .stream()
          .forEachOrdered(File::delete);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Path TEST_SCENARIOS = Path.of("target", "test", "scenarios");

  @BeforeAll
  static void beforeEach() {
    deleteDirectoryRecursively(TEST_SCENARIOS);
  }

  private static final Path EXPECTED_BASE = Path.of("src", "test", "resources", "expected");

  private static final Path EXPECTED_NR_001 = EXPECTED_BASE.resolve("check_single_level_project_structure");

  private static final Path EXPECTED_NR_002 = EXPECTED_BASE.resolve("check_two_level_project_structure");

  private static final Path EXPECTED_NR_003 = EXPECTED_BASE.resolve("check_three_level_project_structure");

  @Test
  @DisplayName("This will check a single level structure with only a single child.")
  void checkSingleLevelProjectStructure() {
    Path rootLevel = TEST_SCENARIOS.resolve("check_single_level_project_structure");
    new SetupMultiLevelScenario(1, 1, rootLevel).create();

    assertThat(rootLevel).isDirectory().satisfies(level1 -> {
      assertThat(level1.resolve("pom.xml")).isNotEmptyFile();
      verify(level1.resolve("pom.xml"), EXPECTED_NR_001.resolve("pom.xml"));

      assertThat(level1.resolve(Path.of("mp-lev-01-00000"))).isDirectory().satisfies(level2 -> {
        verify(level2.resolve("pom.xml"), EXPECTED_NR_001.resolve("mp-lev-01-00000").resolve("pom.xml"));
      });
    });
  }

  @Test
  @DisplayName("This will check a two level structure with only a single child.")
  void checkTwoLevelProjectStructure() {
    Path rootLevel = TEST_SCENARIOS.resolve("check_two_level_project_structure");
    new SetupMultiLevelScenario(1, 2, rootLevel).create();

    assertThat(rootLevel).isDirectory().satisfies(level1 -> {
      assertThat(level1.resolve("pom.xml")).isNotEmptyFile();
      verify(level1.resolve("pom.xml"), EXPECTED_NR_002.resolve("pom.xml"));

      assertThat(level1.resolve(Path.of("mp-lev-01-00000"))).isDirectory().satisfies(level2 -> {
        verify(level2.resolve("pom.xml"), EXPECTED_NR_002.resolve("mp-lev-01-00000").resolve("pom.xml"));
        assertThat(level2.resolve("pom.xml")).isNotEmptyFile();
      });
    });
  }

  @Test
  @DisplayName("This will check a three level structure with only a single child.")
  void checkThreeLevelProjectStructure() {
    Path rootLevel = TEST_SCENARIOS.resolve("check_three_level_project_structure");
    new SetupMultiLevelScenario(1, 3, rootLevel).create();

    assertThat(rootLevel).isDirectory().satisfies(level0 -> {
      verify(level0.resolve("pom.xml"), EXPECTED_NR_003.resolve("pom.xml"));

      assertThat(level0.resolve(Path.of("mp-lev-01-00000"))).isDirectory().satisfies(level1 -> {
        verify(level1.resolve("pom.xml"), EXPECTED_NR_003.resolve("mp-lev-01-00000").resolve("pom.xml"));
        assertThat(level1.resolve(Path.of("mp-lev-02-00000"))).isDirectory().satisfies(level2 -> {
          verify(level2.resolve("pom.xml"), EXPECTED_NR_003.resolve("mp-lev-01-00000").resolve("mp-lev-02-00000").resolve("pom.xml"));
          assertThat(level2.resolve(Path.of("mp-lev-03-00000"))).isDirectory().satisfies(level3 -> {
            verify(level3.resolve("pom.xml"), EXPECTED_NR_003.resolve("mp-lev-01-00000").resolve("mp-lev-02-00000").resolve("mp-lev-03-00000").resolve("pom.xml"));
          });
        });
      });
    });
  }
}