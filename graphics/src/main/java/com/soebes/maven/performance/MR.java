package com.soebes.maven.performance;

import java.util.List;

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
