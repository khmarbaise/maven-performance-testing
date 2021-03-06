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

public class Dependency {
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final String classifier;
  private final Scope scope;

  public enum Scope {
    COMPILE,
    PROVIDED,
    RUNTIME,
    TEST,
    // system => we do not support this because it's deprecated.
    IMPORT
  }

  public Dependency(String groupId, String artifactId, String version, String classifier, Scope scope) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.classifier = classifier;
    this.scope = scope;
  }

  public Dependency(String groupId, String artifactId, String version) {
    this(groupId, artifactId, version, null, Scope.COMPILE);
  }

  public Dependency(String groupId, String artifactId) {
    this(groupId, artifactId, null, null, Scope.COMPILE);
  }

  public static Dependency of(String groupId, String artifactId, String version) {
    return new Dependency(groupId, artifactId, version);
  }

  public static Dependency of(String groupId, String artifactId, Scope scope) {
    return new Dependency(groupId, artifactId, null, null, scope);
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }

  public String getClassifier() {
    return classifier;
  }

  public Scope getScope() {
    return this.scope;
  }
}
