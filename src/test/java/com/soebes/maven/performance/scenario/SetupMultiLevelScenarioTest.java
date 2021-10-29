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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;
import static java.util.stream.Collectors.toList;

/**
 * @author Karl Heinz Marbaise
 */
class SetupMultiLevelScenarioTest {

  @Test
  void test_delete_directory() throws IOException {
//    Path rootLevel = Path.of("target", "test", "scenarios");
    Path rootLevel = Path.of("target", "classes");

    try (Stream<Path> streamOfPath = walk(rootLevel)) {
      List<Path> collect = streamOfPath.filter(s -> Files.isDirectory(s)).sorted(Comparator.reverseOrder()).collect(toList());
      collect.stream().forEachOrdered(s -> System.out.println("s = " + s));

    }
  }

  @Test
  void name() {

    Path rootLevel = Path.of("target", "test", "scenarios", String.format("number-of-module-%04d", 1));
    new SetupMultiLevelScenario(5, 4, rootLevel).create();
  }
}