package com.soebes.maven.performance.mvnversions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Find the locally installed Maven versions which have been installed via
 *
 * @author Karl Heinz Marbaise
 */
public class MavenVersions {

  private final Path downloadDirectory;


  public MavenVersions(Path downloadDirectory) {
    this.downloadDirectory = downloadDirectory;
  }

  public List<Path> listOfMavenVersions() {
    try (Stream<Path> walk = Files.list(downloadDirectory)) {
      return walk.filter(Files::isDirectory)
          .sorted().toList();
    } catch (IOException e) {
      //TODO: Need to reconsider.
      throw new RuntimeException("IOException happened", e);
    }
  }

}
