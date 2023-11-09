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

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Karl Heinz Marbaise
 */
public class Writer {
  private Writer() {
    //intentionally empty.
  }

  public static void to(Model model, Path path) {
    MavenXpp3Writer mavenXpp3Writer = new MavenXpp3Writer();

    try {
      try (OutputStream outputStream = Files.newOutputStream(path)) {
        mavenXpp3Writer.write(outputStream, model);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
