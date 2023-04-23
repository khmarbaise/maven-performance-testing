package com.soebes.maven.performance.json;

import java.util.List;

public class JSONMeasurements {

  private List<JSONSingleResult> results;

  public JSONMeasurements() {
  }

  public JSONMeasurements(List<JSONSingleResult> results) {
    this.results = results;
  }

  public List<JSONSingleResult> getResults() {
    return results;
  }
}
