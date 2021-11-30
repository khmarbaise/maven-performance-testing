package com.soebes.maven.performance.sdkman;

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
      assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> sdkManJDKS.listOfJdks())
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