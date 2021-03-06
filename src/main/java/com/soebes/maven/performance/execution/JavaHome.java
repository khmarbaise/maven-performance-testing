package com.soebes.maven.performance.execution;

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
 * @implSpec Unfortunately currently hard coded. Need to be in sync with configuration in Ansible playbook
 * {@code setup-performance-system.yml} Need to find a better way!
 * @author Karl Heinz Marbaise
 */
public enum JavaHome {
  JDK8(".sdkman/candidates/java/8.0.302-open"),
  //JDK11(".sdkman/candidates/java/11.0.12-tem"),
  JDK11(".sdkman/candidates/java/11.0.12-open"),
  JDK17(".sdkman/candidates/java/17.0.1-open");

  private String javaHome;

  JavaHome(String location) {
    this.javaHome = location;
  }

  public String javaHome() {
    return javaHome;
  }
}
