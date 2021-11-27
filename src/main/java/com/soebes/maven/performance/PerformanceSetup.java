package com.soebes.maven.performance;

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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.JCommander.Builder;
import com.soebes.maven.performance.commands.CommandMain;
import com.soebes.maven.performance.commands.Commands;
import com.soebes.maven.performance.commands.Execute;
import com.soebes.maven.performance.commands.Scenario;
import com.soebes.maven.performance.execution.ExecuteHyperfine;
import com.soebes.maven.performance.execution.ExecutionResult;
import com.soebes.maven.performance.scenario.SetupMultiLevelScenario;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Karl Heinz Marbaise
 */
class PerformanceSetup {

  public static void main(String[] args) throws IOException {
    CommandMain cmdMain = new CommandMain();

    Builder builder = JCommander.newBuilder()
        .addObject(cmdMain);

    Arrays.stream(Commands.values()).forEach(s -> builder.addCommand(s.command(), s.invoker(), s.aliases()));

    JCommander jCommander = builder.build();
    jCommander.parse(args);

    if (cmdMain.isHelp() || Objects.isNull(jCommander.getParsedCommand())) {
      jCommander.usage();
      return;
    }

    Commands command = Commands.to(jCommander.getParsedCommand());
    if (command.invoker().isHelp()) {
      jCommander.usage();
      return;
    }

    switch (command) {
      case EXECUTE -> cmdExecute((Execute)command.invoker());
      case SCENARIO -> cmdScenario((Scenario) command.invoker());
    }
  }

  private static void cmdScenario(Scenario invoker) {
    System.out.println("PerformanceSetup.executeScenario");
    invoker.getNumberOfModules().stream().forEachOrdered(nofm -> {
      Path rootLevel = Path.of("target", "scenarios", String.format("number-of-module-%04d", nofm));
      System.out.println("Generating Scenario " + nofm);
      new SetupMultiLevelScenario(nofm, 1, rootLevel).create();
      //new SetupSingleLevelScenario(nofm, rootLevel).setup();
    });

  }

  private static void cmdExecute(Execute invoker) {
    Path basePath = Path.of("target", "scenarios");
    ExecuteHyperfine executeHyperfine = new ExecuteHyperfine();
    // -w 5 warmup round 5
    // --export-markdown ../src/site/markdown/results-${JDK}.md -L VERSION "${VERSIONS[*]}" -n {VERSION} ../apache-maven-{VERSION}/bin/mvn clean )
    //String versions="3.0.5,3.1.1,3.2.5,3.3.9,3.5.4,3.6.3,3.8.1,3.8.2,3.8.3,3.8.4";
    String versions="3.8.4";
    String result = String.format("result-%s-%s.md", invoker.jdk(), invoker.numberOfModules());
    String moduleDirectory = String.format("number-of-module-%04d", invoker.numberOfModules());
    Path downloadsDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().resolve("downloads");
    ExecutionResult exec = executeHyperfine.exec(Paths.get(basePath.toString(), moduleDirectory).toFile(),
        List.of("-w", "5", "--export-markdown", result
        , "-L", "VERSION", versions, "-n", "{VERSION}", downloadsDirectory + "/apache-maven-{VERSION}/bin/mvn clean | tee mvn.log 2>mvn-error.log"));
    System.out.println(exec.getStdErr());
    System.out.println(exec.getStdOut());
    exec.getReturnCode();
  }
}
