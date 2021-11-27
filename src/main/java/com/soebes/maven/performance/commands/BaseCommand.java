package com.soebes.maven.performance.commands;

import com.beust.jcommander.Parameter;

public class BaseCommand {
  @Parameter(names = "--help", help = true)
  private boolean help;

  public boolean isHelp() {
    return help;
  }
}
