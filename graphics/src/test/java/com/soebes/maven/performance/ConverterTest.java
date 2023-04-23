package com.soebes.maven.performance;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  @Test
  void secondReading() {
    Path basePath = Path.of("json");

    record JDK(String jdk) { }
    record MVN(String mvn) { }
    record MR(double mean,
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
    // NoM (NumberOfModules)
    //  JDK         MVN   NoM,
    Map<JDK, Map<MVN, Map<Integer, MR>>> results = new HashMap<>();

    var measurements = Converter.readMeasurementFile(basePath.resolve("results-20-open-10.json"));
    measurements.getResults().forEach(m -> System.out.println("m = " + m.getCommand() + " " + m.getMean()));
  }

}