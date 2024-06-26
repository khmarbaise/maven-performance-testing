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
 * Define different default sets of properties for different kind
 * of example builds.
 *
 * @author Karl Heinz Marbaise
 */
public final class BuildProperties {

  private BuildProperties() {
    // intentionally empty.
  }

  /**
   * Building for JDK 8 runtime.
   */
  public static final List<Property> JAVA_8 = List.of(
      new Property("project.build.sourceEncoding", "UTF-8"),
      new Property("maven.compiler.target", "1.8"),
      new Property("maven.compiler.source", "1.8")
  );
  /**
   * Building for JDK 9+ runtime.
   */
  public static final List<Property> JAVA_8_PLUS = List.of(
      new Property("project.build.sourceEncoding", "UTF-8"),
      new Property("maven.compiler.release", "8")
  );

}
