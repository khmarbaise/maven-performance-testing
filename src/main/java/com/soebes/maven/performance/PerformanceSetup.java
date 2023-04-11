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
import com.soebes.maven.performance.mvnversions.MavenVersions;
import com.soebes.maven.performance.scenario.SetupMultiLevelScenario;
import com.soebes.maven.performance.sdkman.SDKManJDKS;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Karl Heinz Marbaise
 */
class PerformanceSetup {

  public static void main(String[] args) {
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

    SDKManJDKS sdkManJDKS = new SDKManJDKS(Path.of(System.getProperty("user.home")));
    List<Path> listOfJDKS = sdkManJDKS.listOfJdks();
    listOfJDKS.forEach(s -> System.out.println("JDKS: " + s));

    Path rootTestDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
    Path downloadsDirectory = rootTestDirectory.resolve("downloads");

    var mavenVersions = new MavenVersions(downloadsDirectory);
    var paths = mavenVersions.listOfMavenVersions();
    var versions = paths.stream().map(Path::getFileName).map(s -> s.toString().substring(13)).toList();

    paths.forEach(s -> System.out.println("MVN = " + s.getFileName()));
    switch (command) {
      case EXECUTE -> executeMeasurement(command, sdkManJDKS, versions);
      case SCENARIO -> cmdScenario((Scenario) command.invoker());
    }
  }

  private static void executeMeasurement(Commands command, SDKManJDKS sdkManJDKS, List<String> mavenVersions) {
    ExecuteMeasurement executeMeasurement = new ExecuteMeasurement(((Execute) command.invoker()).numberOfModules(), sdkManJDKS.listOfJdks(), mavenVersions);
    executeMeasurement.measurement();
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

}
