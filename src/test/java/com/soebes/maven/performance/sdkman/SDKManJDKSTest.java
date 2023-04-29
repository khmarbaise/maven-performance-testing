package com.soebes.maven.performance.sdkman;

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

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SDKManJDKSTest {

  @Test
  void sdk_man_not_existing_at_all() throws IOException {
    try (FileSystem linuxSystem = MemoryFileSystemBuilder.newLinux().build()) {

      Path homeDirectory = linuxSystem.getPath("/", "home", "test");
      Files.createDirectories(homeDirectory);

      SDKManJDKS sdkManJDKS = new SDKManJDKS(homeDirectory.getRoot().resolve("/").resolve("home").resolve("test"));
      assertThatExceptionOfType(RuntimeException.class).isThrownBy(sdkManJDKS::listOfJdks)
          .withCause(new NoSuchFileException("/home/test/.sdkman/candidates/java"));
    }

  }

  @Test
  void no_jdk_available() throws IOException {
    try (FileSystem linuxSystem = MemoryFileSystemBuilder.newLinux().build()) {

      Path homeDirectory = linuxSystem.getPath("/", "home", "test", ".sdkman", "candidates", "java");
      Files.createDirectories(homeDirectory);

      SDKManJDKS sdkManJDKS = new SDKManJDKS(homeDirectory.getRoot().resolve("/").resolve("home").resolve("test"));
      assertThat(sdkManJDKS.listOfJdks()).isEmpty();
    }

  }

  @Test
  void one_jdk_available() throws IOException {
    try (FileSystem linuxSystem = MemoryFileSystemBuilder.newLinux().build()) {

      Path homeDirectory = linuxSystem.getPath("/", "home", "test", ".sdkman", "candidates", "java", "17.0.1-tem");
      Files.createDirectories(homeDirectory);

      SDKManJDKS sdkManJDKS = new SDKManJDKS(homeDirectory.getRoot().resolve("/").resolve("home").resolve("test"));
      List<Path> actual = sdkManJDKS.listOfJdks();
      assertThat(actual)
          .containsExactly(homeDirectory.getRoot()
              .resolve("/")
              .resolve("home")
              .resolve("test")
              .resolve(".sdkman")
              .resolve("candidates")
              .resolve("java")
              .resolve("17.0.1-tem"));
    }

  }

}