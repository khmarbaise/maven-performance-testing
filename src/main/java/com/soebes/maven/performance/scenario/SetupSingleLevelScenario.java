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

import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

import static com.soebes.maven.performance.helper.PomHelper.writePom;
import static com.soebes.maven.performance.maven.BuildProperties.JAVA_7;

/**
 * This scenario produces a multi-module build
 * which consists of a given number of modules
 * with one hierarchie level. That means in other
 * words only a single level of modules.
 *
 * @author Karl Heinz Marbaise
 */
public class SetupSingleLevelScenario implements Scenario {

  private static final String PARENT_POM_G = "org.test.performance";

  private static final String PARENT_POM_GAV = "org.test.performance:scenario-one-parent:1.0-SNAPSHOT";
  private static final String MODULE_POM_GAV = "org.test.performance:scenario-one:1.0-SNAPSHOT";

  private int numberOfModules;

  private Path rootLevel;

  public SetupSingleLevelScenario(int numberOfModules, Path rootLevel) {
    this.numberOfModules = numberOfModules;
    this.rootLevel = rootLevel;
  }

  public void create() {
    List<String> modules = IntStream.range(0, this.numberOfModules)
        .boxed()
        .map(s -> String.format("module-%04d", s))
        .toList();
    CreatePom rootPom = CreatePom.of(PARENT_POM_GAV, "pom")
        .properties(JAVA_7)
        .build()
        .pluginManagement(ApachenMavenPlugins.DEFAULT_PLUGINS)
        .modules(modules.toArray(new String[0]));

    writePom(rootPom, rootLevel, "pom.xml");

    modules.forEach(s -> {
      Path moduleLevel = Path.of(rootLevel.toString(), s);
      CreatePom modulePom = CreatePom.of(PARENT_POM_G, s, null)
          .parent(PARENT_POM_GAV);
      writePom(modulePom, moduleLevel, "pom.xml");
    });

  }

}
