package com.soebes.maven.performance.helper;

import com.soebes.maven.performance.CreatePom;
import com.soebes.maven.performance.Writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class PomHelper {
  public static void writePom(CreatePom createPom, Path directory, String pomFile) {
    Set<PosixFilePermission> perms =
        PosixFilePermissions.fromString("rwxr-xr-x");
    FileAttribute<Set<PosixFilePermission>> attr =
        PosixFilePermissions.asFileAttribute(perms);
    try {
      Files.createDirectories(directory, attr);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Writer.to(createPom.toModel(), Path.of(directory.toString(), pomFile));
  }

}
