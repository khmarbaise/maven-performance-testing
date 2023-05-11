package com.soebes.maven.performance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soebes.maven.performance.json.JSONMeasurements;
import com.soebes.maven.performance.json.JSONSingleResult;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

class Converter {

  private Converter() {
    // Just ignore it.
  }

  static MeasurementRecord convertFromFileNameToJDKModuleInformation(Path nameOfJson) {
    var fileOnly = nameOfJson.getName(nameOfJson.getNameCount() - 1);
    var fileString = fileOnly.toString();

    var split = fileString.substring(0, fileString.length() - 5).split("-");

    var jdk = split[1] + "-" + split[2];
    var numberOfModules = split[3];
    return new MeasurementRecord(jdk, Integer.parseInt(numberOfModules));
  }

  static JSONMeasurements readMeasurementFile(Path jsonFile) {
    try {
      ObjectMapper mapper = new ObjectMapper();

      return mapper.readValue(jsonFile.toFile(), JSONMeasurements.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static Function<JSONSingleResult, MR> convertToMR(JDK jdk, Integer nof) {
    return jsr -> new MR(
        new MVN(new ComparableVersion(jsr.getParameters().get("VERSION"))),
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


}
