package com.soebes.maven.performance.maven;

import org.junit.jupiter.api.Test;

/**
 * @author Karl Heinz Marbaise
 */
class DependencyTest {

  @Test
  void name() {
    Dependency dependency = Dependency.of("groupId", "artifactId", "1.0");
  }
}
