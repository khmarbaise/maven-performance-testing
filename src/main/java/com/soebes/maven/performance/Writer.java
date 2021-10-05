package com.soebes.maven.performance;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Writer {
  public static void to(Model model, Path path) {
    MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();

    try {
      mavenXpp3Writer.write(new FileOutputStream(path.toFile()), model);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
