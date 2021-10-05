package com.soebes.duplicate;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

class WriteTest {

  void write(Model model) throws IOException {
    model.setModelVersion("4.0.0");

    MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();

    Path target = Path.of("target", "pom.xml");
    mavenXpp3Writer.write(new FileOutputStream(target.toFile()), model);
  }

  void with(String gav) {

  }

  @Test
  void name() throws IOException {
    Model model = new Model();

    model.setGroupId("com.soebes.maven.performance.first");
    model.setArtifactId("mini");
    model.setVersion("0.0.1-SNAPSHOT");

    CreatePom.with("com.soebes.maven.performance.first:mini:0.0.1-SNAPSHOT");

    write(model);
  }

  public static class CreatePom {


    static CreatePom with(String gav) {
      return new CreatePom();
    }
  }
}
