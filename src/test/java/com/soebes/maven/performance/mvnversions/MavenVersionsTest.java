package com.soebes.maven.performance.mvnversions;

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

import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;

class MavenVersionsTest {

  @Test
  void name() {
    Path rootTestDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
    Path downloadsDirectory = rootTestDirectory.resolve("downloads");
    var mavenVersions = new MavenVersions(downloadsDirectory);
    var paths = mavenVersions.listOfMavenVersions();

    var versions = paths.stream().map(Path::getFileName).map(s -> s.toString().substring(13)).toList();
    paths.forEach(s -> System.out.println("s = " + s.getFileName()));
    versions.forEach(s-> System.out.println("v = " + s));
  }
}