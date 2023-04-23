package com.soebes.maven.performance;

import com.soebes.maven.performance.json.JSONSingleResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.soebes.maven.performance.Converter.convertFromFileNameToJDKModuleInformation;
import static com.soebes.maven.performance.Converter.readMeasurementFile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
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
    measurements.getResults().forEach(m -> System.out.println("m = " + m.getCommand() + " " + m.getMean()));
  }

  record JDK(String jdk) { }
  record MVN(String mvn) { }
  record MR(MVN mvn,
            JDK jdk,
            Integer numberOfModules,
            double mean,
            double stddev,
            double median,
            double user,
            double system,
            double min,
            double max,
            List<Double> times,
            List<Integer> exit_codes

  ) {
  }

  Function<JSONSingleResult, MR> convertToMR(JDK jdk, Integer nof) {
    return jsr -> new MR(
        new MVN(jsr.getParameters().get("VERSION")),
        jdk,
        nof,
        jsr.getMean(),
        jsr.getStddev(),
        jsr.getMedian(),
        jsr.getUser(),
        jsr.getSystem(),
        jsr.getMin(),
        jsr.getMax(),
        jsr.getTimes(),
        jsr.getExit_codes());
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


    List<MR> collect1 = result.stream().flatMap(s -> s.JSONMeasurements().getResults().stream().map(convertToMR(new JDK(s.jdk()), s.numberOfModules()))).toList();

    // NoM (NumberOfModules)
    //  JDK         MVN   NoM,
    Map<JDK, Map<MVN, Map<Integer, List<MR>>>> collect = collect1.stream().collect(
        groupingBy(s -> s.jdk, groupingBy(k -> k.mvn, groupingBy(l -> l.numberOfModules, toList()))));

    var mrs = collect.get(new JDK("20-open")).get(new MVN("3.8.5")).get(Integer.valueOf(10000));
    System.out.println("mrs = " + mrs);


    var jdks = collect.keySet().stream().toList().stream().toList();

    System.out.println("Number of JDK versions %s, set: %s ".formatted(jdks.size(), jdks) );
  }

}