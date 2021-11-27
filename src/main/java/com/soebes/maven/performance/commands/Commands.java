package com.soebes.maven.performance.commands;

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

import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Heinz Marbaise
 */
public enum Commands {
  SCENARIO("scenario", List.of("sc"), new Scenario()),
  EXECUTE("execute", List.of("exec"), new Execute());

  private final String command;
  private final List<String> aliases;
  private final BaseCommand invoker;

  Commands(String command, List<String> aliases, BaseCommand commandInvoker) {
    this.command = command;
    this.aliases = aliases;
    this.invoker = commandInvoker;
  }

  public String command() {
    return command;
  }

  public String[] aliases() {
    return aliases.toArray(new String[0]);
  }

  public BaseCommand invoker() {
    return invoker;
  }

  public static Commands to(String cmd) {
    return Arrays.stream(values())
        .filter(s -> s.command.equals(cmd.toLowerCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown command given."));
  }
}
