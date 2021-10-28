package com.soebes.maven.performance.scenario;

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

import com.soebes.maven.performance.CreatePom;
import com.soebes.maven.performance.maven.ApachenMavenPlugins;
import com.soebes.maven.performance.maven.GAV;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

import static com.soebes.maven.performance.helper.PomHelper.writePom;
import static com.soebes.maven.performance.maven.BuildProperties.JAVA_7;
import static java.util.stream.Collectors.toList;

/**
 * @author Karl Heinz Marbaise
 */
public class SetupMultiLevelScenario implements Scenario {

  private static final String PARENT_VERSION = "1.0-SNAPSHOT";

  private static final GAV ROOT_PARENT_GAV = GAV.of("org.test.performance.multi.level", "scenario-one-parent", PARENT_VERSION);

  private int numberOfModules;

  private int numberOfLevels;

  private Path rootLevel;

  public SetupMultiLevelScenario(int numberOfModules, int numberOfLevels, Path rootLevel) {
    this.numberOfModules = numberOfModules;
    this.numberOfLevels = numberOfLevels;
    this.rootLevel = rootLevel;
  }

  public void create() {
    List<String> rootModuleNames = IntStream.range(0, this.numberOfModules)
        .boxed()
        .map(s -> String.format("-> mp-lev-%02d-%05d", 1, s))
        .collect(toList());

    CreatePom rootPom = CreatePom.of(ROOT_PARENT_GAV, "pom")
        .properties(JAVA_7)
        .build()
        .pluginManagement(ApachenMavenPlugins.DEFAULT_PLUGINS)
        .modules(rootModuleNames.toArray(new String[0]));

    writePom(rootPom, rootLevel, "pom.xml");
    rootModuleNames.stream().forEachOrdered(module -> createSubLevel(ROOT_PARENT_GAV, rootLevel, module, 1));

  }

  private void createSubLevel(GAV parentGAV, Path rootLevel, String module, int level) {
    List<String> subLevelModules = IntStream.range(0, this.numberOfModules)
        .boxed()
        .map(s -> String.format("XXXXXmp-lev-%02d-%05d", level + 1, s))
        .collect(toList());

    Path dirModuleLevel = Path.of(rootLevel.toString(), module);

    GAV newPom = GAV.of(parentGAV.getGroupId() + String.format(".%02d", level), module, parentGAV.getVersion());
    CreatePom modulePom = CreatePom.of(newPom, "pom")
        .parent(parentGAV)
        .modules(subLevelModules.toArray(new String[0]));
    writePom(modulePom, dirModuleLevel, "pom.xml");

//    if (level >= this.numberOfLevels) {
//      subLevelModules.stream().forEachOrdered(s -> {
//        createFinalLevel(newPom, Path.of(dirModuleLevel.toString(), s), module, level + 1);
//      });
//    } else {
//      createSubLevel(newPom, Path.of(rootLevel.toString(), module), module, level + 1);
//    }

  }

  private void createFinalLevel(GAV parentGAV, Path rootLevel, String module, int level) {
    Path dirModuleLeven = Path.of(rootLevel.toString(), module);
    GAV newPom = GAV.of(parentGAV.getGroupId() + String.format(".%02d", level), module, parentGAV.getVersion());
    CreatePom modulePom = CreatePom.of(newPom, "pom")
        .parent(parentGAV);
    writePom(modulePom, dirModuleLeven, "pom.xml");
  }

}
