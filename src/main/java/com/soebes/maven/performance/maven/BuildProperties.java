package com.soebes.maven.performance.maven;

import java.util.List;

/**
 * Define different default sets of properties for different kind
 * of example builds.
 *
 * @author Karl Heinz Marbaise
 */
public final class BuildProperties {

  private BuildProperties() {
    // intentionally empty.
  }

  /**
   * Building for JDK 7 runtime.
   */
  public static final List<Property> JAVA_7 = List.of(
      new Property("project.build.sourceEncoding", "UTF-8"),
      new Property("maven.compiler.target", "1.7"),
      new Property("maven.compiler.source", "1.7")
  );

}
