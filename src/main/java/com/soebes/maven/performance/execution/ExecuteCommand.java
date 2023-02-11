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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Heinz Marbaise
 */
public class ExecuteCommand {

  public ExecuteCommand() {
  }

  public ExecutionResult exec(List<String> applications, List<String> startArguments, File workingDirectory) {

    ProcessBuilder pb = new ProcessBuilder();

    var args = new ArrayList<>(applications);
    args.addAll(startArguments);
    pb.command(args);
    pb.directory(workingDirectory);
    System.out.println("args = " + args);
    try {
      Process start = pb.start();
      int returnCode = start.waitFor();
      String result = new String(start.getInputStream().readAllBytes());
      String errorOut = new String(start.getErrorStream().readAllBytes());
      return new ExecutionResult(result, errorOut, returnCode);
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
  }

}
