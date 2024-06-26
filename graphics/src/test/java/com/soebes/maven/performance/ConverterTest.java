package com.soebes.maven.performance;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.soebes.maven.performance.Converter.convertFromFileNameToJDKModuleInformation;
import static com.soebes.maven.performance.Converter.convertToMR;
import static com.soebes.maven.performance.Converter.readMeasurementFile;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {

  @Test
  void jdkModule() {
    var basePath = Path.of("json");
    var jsonFileName = basePath.resolve("results-20-open-10.json");
    var convert = Converter.convertFromFileNameToJDKModuleInformation(jsonFileName);
    assertThat(convert.jdk()).isEqualTo("20-open");
    assertThat(convert.numberOfModules()).isEqualTo(10);
  }

  @Test
  void firstReading() {
    Path basePath = Path.of("json");

    var measurements = Converter.readMeasurementFile(basePath.resolve("results-20-open-10.json"));
    measurements.results().forEach(m -> System.out.println("m = " + m.command() + " " + m.mean()));
  }


  @Test
  void secondReading() throws IOException {
    Path basePath = Path.of("json");

    var result = Selector.selectJsonFilesOnly(basePath).stream()
        .map(p -> {
          var mr = convertFromFileNameToJDKModuleInformation(p);
          var measurements  = readMeasurementFile(p);
          return new Result(mr.jdk(), mr.numberOfModules(), measurements);
        }).toList();


    List<MR> collect1 = result.stream().flatMap(s -> s.JSONMeasurements().results().stream().map(convertToMR(new JDK(s.jdk()), s.numberOfModules()))).toList();

    // NoM (NumberOfModules)
    //  JDK         MVN   NoM,
    Map<JDK, Map<MVN, Map<Integer, MR>>> collect = collect1.stream().collect(
        groupingBy(MR::jdk, groupingBy(MR::mvn, toMap(MR::numberOfModules, identity()))));

    var jdkList = collect.keySet().stream().sorted(Comparator.comparing(jdk -> new ComparableVersion(jdk.jdk()))).toList();
    for (JDK jdk : jdkList) {
      System.out.println("jdk = " + jdk);
      var mvnList = collect.get(jdk).keySet().stream().sorted(Comparator.comparing(MVN::mvn)).toList();
      System.out.println("mvnList = " + mvnList);
      for (MVN mvn : mvnList) {
        System.out.println();
        System.out.println("mvn = " + mvn);
        Map<Integer, MR> nomMR = collect.get(jdk).get(mvn);
        List<Map.Entry<Integer, MR>> sortedNomList = nomMR.entrySet().stream().sorted((Comparator.comparingInt(Map.Entry::getKey))).toList();
        for (Map.Entry<Integer, MR> entry : sortedNomList) {
          System.out.println("entry = " + entry.getKey());
          System.out.println("entry = " + entry.getValue().mean());
        }

      }

    }

//    var mrs = collect.get(JDK_CONST).get(new MVN(new ComparableVersion("3.8.5"))).get(10000);
//    System.out.println("mrs = " + mrs);
//
//
//    var jdks = collect.keySet().stream().toList().stream().toList();
//
//    System.out.printf("Number of JDK versions %s, set: %s %n", jdks.size(), jdks);
//    System.out.printf("Number of MVS versions %s, set: %s %n", mvnList.size(), mvnList);

  }

}