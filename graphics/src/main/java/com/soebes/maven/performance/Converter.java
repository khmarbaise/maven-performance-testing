package com.soebes.maven.performance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soebes.maven.performance.json.JSONMeasurements;
import com.soebes.maven.performance.json.JSONSingleResult;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

final class Converter {

  private Converter() {
    // Just ignore it.
  }

  static MeasurementRecord convertFromFileNameToJDKModuleInformation(Path nameOfJson) {
    var fileOnly = nameOfJson.getName(nameOfJson.getNameCount() - 1);
    var fileString = fileOnly.toString();

    // results-17.0.10-tem-20.json
    // !                    !
    // +--------------------+- 5 +
    // Remove file extension (.json)
    // "results-17.0.10-tem-20"
    //  +-----+ +-----+ +-+ ++
    //   !       !       !  !
    //  [0]     [1]     [2] [3]
    var split = fileString.substring(0, fileString.length() - 5).split("-");

    var jdkName = split[1] + "-" + split[2];
    var numberOfModules = split[3];
    return new MeasurementRecord(jdkName, Integer.parseInt(numberOfModules));
  }

  static JSONMeasurements readMeasurementFile(Path jsonFile) {
    try {
      ObjectMapper mapper = new ObjectMapper();

      return mapper.readValue(jsonFile.toFile(), JSONMeasurements.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static Function<JSONSingleResult, MR> convertToMR(JDK jdk, Integer numberOfModules) {
    return jsonSingleResult -> new MR(
        new MVN(new ComparableVersion(jsonSingleResult.parameters().get("VERSION"))),
        jdk,
        numberOfModules,
        jsonSingleResult.mean(),
        jsonSingleResult.stddev(),
        jsonSingleResult.median(),
        jsonSingleResult.user(),
        jsonSingleResult.system(),
        jsonSingleResult.min(),
        jsonSingleResult.max(),
        jsonSingleResult.times(),
        jsonSingleResult.exit_codes());
  }


}
