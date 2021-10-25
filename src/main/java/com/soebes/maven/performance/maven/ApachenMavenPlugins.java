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

import java.util.List;

/**
 * Define the usual list of Maven plugins which are used for a Maven build.
 *
 * @author Karl Heinz Marbaise
 */
public final class ApachenMavenPlugins {

  private ApachenMavenPlugins() {
    // intentionally empty.
  }

  private static final String APACHE_GROUPID = "org.apache.maven.plugins";

  public static final Plugin MAVEN_CLEAN_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-clean-plugin", "3.1.0");
  public static final Plugin MAVEN_COMPILER_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-compiler-plugin", "3.8.1");
  public static final Plugin MAVEN_RESOURCES_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-resources-plugin", "3.1.0");
  public static final Plugin MAVEN_JAR_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-jar-plugin", "3.2.0");
  public static final Plugin MAVEN_INSTALL_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-install-plugin", "2.5.2");
  public static final Plugin MAVEN_DEPLOY_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-deploy-plugin", "2.8.2");
  public static final Plugin MAVEN_SUREFIRE_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-surefire-plugin", "2.22.2");
  public static final Plugin MAVEN_FAILSAFE_PLUGIN = Plugin.of(APACHE_GROUPID, "maven-failsafe-plugin", "2.22.2");

  public static final List<Plugin> DEFAULT_PLUGINS = List.of(
      MAVEN_CLEAN_PLUGIN,
      MAVEN_RESOURCES_PLUGIN,
      MAVEN_COMPILER_PLUGIN,
      MAVEN_DEPLOY_PLUGIN,
      MAVEN_JAR_PLUGIN,
      MAVEN_INSTALL_PLUGIN,
      MAVEN_SUREFIRE_PLUGIN,
      MAVEN_FAILSAFE_PLUGIN
  );

}
