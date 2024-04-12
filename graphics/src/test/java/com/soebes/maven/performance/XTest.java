package com.soebes.maven.performance;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static com.soebes.maven.performance.Converter.convertFromFileNameToJDKModuleInformation;
import static com.soebes.maven.performance.Converter.readMeasurementFile;
import static java.util.function.Function.identity;

class XTest {

  @Test
  void name() throws IOException {
    var basepath = Path.of("json");
    var result = Selector.selectJsonFilesOnly(basepath).stream()
        .map(p -> {
          var mr = convertFromFileNameToJDKModuleInformation(p);
          var measurements = readMeasurementFile(p);
          return new Result(mr.jdk(), mr.numberOfModules(), measurements);
        }).toList();


    var jdkList = result.stream().collect(Collectors.toMap(Result::jdk, identity()));

    jdkList.forEach((k, v) -> System.out.println("k:" + k + " v:" + v.JSONMeasurements().results().get(0).parameters().get("VERSION")));
  }
}
