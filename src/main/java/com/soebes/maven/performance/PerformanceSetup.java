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
import com.soebes.maven.performance.commands.CommandMain;
import com.soebes.maven.performance.commands.Scenario;
import com.soebes.maven.performance.scenario.SetupMultiLevelScenario;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Karl Heinz Marbaise
 */
class PerformanceSetup {

  public static void main(String[] args) throws IOException {
    CommandMain cmdMain = new CommandMain();
    Scenario scenario = new Scenario();

    JCommander jCommander = JCommander.newBuilder()
        .addObject(cmdMain)
        .addCommand("scenario", scenario, "sc")
        .build();
    jCommander.parse(args);

    if (cmdMain.isHelp()) {
      jCommander.usage();
      return;
    }

    List<Integer> numberOfModules =
        scenario.getNumberOfModules();

    numberOfModules.stream().forEachOrdered(nofm -> {
      Path rootLevel = Path.of("target", "scenarios", String.format("number-of-module-%04d", nofm));
      System.out.println("Generating Scenario " + nofm);
      new SetupMultiLevelScenario(nofm, rootLevel).setup();
      //new SetupSingleLevelScenario(nofm, rootLevel).setup();
    });


  }
}
