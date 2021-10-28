package com.soebes.maven.performance.scenario;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class SetupMultiLevelScenarioTest {

  @Test
  void name() {
    Path rootLevel = Path.of("target", "test", "scenarios", String.format("number-of-module-%04d", 1));
    new SetupMultiLevelScenario(2, 2, rootLevel).create();
  }
}