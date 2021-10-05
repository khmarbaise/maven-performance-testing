package com.soebes.maven.performance;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class PomTestProjects {

  public static void main(String[] args) throws IOException {

    Model model = new Model();
    MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();
    mavenXpp3Writer.write(new FileOutputStream(new File("pom.xml")), model);
  }
}
