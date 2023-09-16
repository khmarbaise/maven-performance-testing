package com.soebes.maven.performance.maven;

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

import static com.soebes.maven.performance.maven.Dependency.Scope.COMPILE;

public record Dependency(String groupId, String artifactId, String version, String classifier, Scope scope) {

  public enum Scope {
    COMPILE,
    PROVIDED,
    RUNTIME,
    TEST,
    // system => we do not support this because it's deprecated.
    IMPORT
  }

  public Dependency(String groupId, String artifactId, String version) {
    this(groupId, artifactId, version, null, COMPILE);
  }

  public Dependency(String groupId, String artifactId) {
    this(groupId, artifactId, null, null, COMPILE);
  }

  public static Dependency of(String groupId, String artifactId) {
    return new Dependency(groupId, artifactId, null, null, COMPILE);
  }

  public static Dependency of(String groupId, String artifactId, String version) {
    return new Dependency(groupId, artifactId, version);
  }

  public static Dependency of(String groupId, String artifactId, Scope scope) {
    return new Dependency(groupId, artifactId, null, null, scope);
  }

}
