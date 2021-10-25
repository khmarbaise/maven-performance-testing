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

/**
 * Defines the available Codehaus Maven Plugins.
 *
 * @author Karl Heinz Marbaise
 */
public final class MojoHausMavenPlugins {
  private MojoHausMavenPlugins() {
    // intentionally empty.
  }

  private static final String MOJO_CODEHAUS_GROUPID = "org.codehaus.mojo";
  public static final Plugin VERSION_MAVEN_PLUGIN = Plugin.of(MOJO_CODEHAUS_GROUPID, "versions-maven-plugin", "2.8.1");

}
