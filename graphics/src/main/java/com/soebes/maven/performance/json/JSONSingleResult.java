package com.soebes.maven.performance.json;

import java.util.List;
import java.util.Map;

public record JSONSingleResult (
    String command,
    double mean,
    double stddev,
    double median,
    double user,
    double system,
    double min,
    double max,
    List<Double> times,
    List<Integer> exit_codes,
    Map<String, String> parameters
){
}
