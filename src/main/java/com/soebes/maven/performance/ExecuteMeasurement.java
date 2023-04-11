package com.soebes.maven.performance;

import com.soebes.maven.performance.execution.ExecuteCommand;
import com.soebes.maven.performance.execution.ExecutionResult;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ExecuteMeasurement {

  private final Integer numberOfModules;
  private final List<Path> jdkVersions;
  private final List<String> mavenVersions;

  public ExecuteMeasurement(Integer numberOfModules, List<Path> jdkVersions, List<String> mavenVersions) {
    this.numberOfModules = numberOfModules;
    this.jdkVersions = jdkVersions;
    this.mavenVersions = mavenVersions;
  }

  public void measurement() {
    jdkVersions.forEach(jdk -> cmdExecute(numberOfModules, jdk));
  }

  void cmdExecute(Integer numberOfModules, Path jdkVersion) {

    System.out.println("Executing measurement for numberOfModules = " + numberOfModules + " with:" + jdkVersion.getFileName());

    Path basePath = Path.of("target", "scenarios");
    // -w 5 warmup round 5
    // --export-markdown ../src/site/markdown/results-${JDK}.md -L VERSION "${VERSIONS[*]}" -n {VERSION} ../apache-maven-{VERSION}/bin/mvn clean )
    String result = "results-%s-%s.md".formatted(jdkVersion.getFileName(), numberOfModules);
    String resultJson = "results-%s-%s.json".formatted(jdkVersion.getFileName(), numberOfModules);
    String moduleDirectory = "number-of-module-%04d".formatted(numberOfModules);
    Path rootTestDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
    Path downloadsDirectory = rootTestDirectory.resolve("downloads");
    Path markdownDirectory = rootTestDirectory.resolve("src").resolve("site").resolve("markdown");

    String prepareCommand = "JAVA_HOME=" + jdkVersion;

    //Need to reconsider if using .mavenrc is a good idea?
    //Heap setting for all tests the same!
    writeMavenRc();
    String commandToExecute = downloadsDirectory + "/apache-maven-{VERSION}/bin/mvn -V clean | tee mvn-{VERSION}-" + jdkVersion.getFileName() + ".log 2>mvn-{VERSION}-" + jdkVersion.getFileName() + "-error.log";
    ExecuteCommand executeCommand = new ExecuteCommand();

    ExecutionResult exec = executeCommand.exec(List.of("hyperfine"), List.of("-w", "5", "--export-markdown", markdownDirectory + "/" + result,
            "--export-json", markdownDirectory + "/" + resultJson,
            "--shell", "bash",
//            "-p", prepareCommand,
            "-L", "VERSION", String.join(",", this.mavenVersions), "-n", "{VERSION}", prepareCommand + ";" + commandToExecute), Paths.get(basePath.toString(), moduleDirectory).toFile()
    );
    System.out.println(exec.stdErr());
    System.out.println(exec.stdOut());
    System.out.println("exec = " + exec.returnCode());
  }


  void writeMavenRc() {
    String javaOpts = "export JAVA_OPTS=-Xmx12G";
    //FIXME: Home directory hard coded.
    try {
      Files.writeString(Path.of("/home", "tmpt").resolve(".mavenrc"), javaOpts, StandardOpenOption.CREATE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
