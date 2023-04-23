package com.soebes.maven.performance;

import com.soebes.maven.performance.json.JSONMeasurements;

record Result(String jdk, Integer numberOfModules, JSONMeasurements JSONMeasurements) {
}
