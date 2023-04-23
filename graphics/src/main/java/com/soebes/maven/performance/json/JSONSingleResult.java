package com.soebes.maven.performance.json;

import java.util.List;
import java.util.Map;

public class JSONSingleResult {

  private String command;
  private double mean;
  private double stddev;
  private double median;
  private double user;
  private double system;
  private double min;
  private double max;
  private List<Double> times;
  private List<Integer> exit_codes;
  private Map<String, String> parameters;

  public JSONSingleResult() {
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public void setMean(double mean) {
    this.mean = mean;
  }

  public void setStddev(double stddev) {
    this.stddev = stddev;
  }

  public void setMedian(double median) {
    this.median = median;
  }

  public void setUser(double user) {
    this.user = user;
  }

  public void setSystem(double system) {
    this.system = system;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public void setTimes(List<Double> times) {
    this.times = times;
  }

  public void setExit_codes(List<Integer> exit_codes) {
    this.exit_codes = exit_codes;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  public String getCommand() {
    return command;
  }

  public double getMean() {
    return mean;
  }

  public double getStddev() {
    return stddev;
  }

  public double getMedian() {
    return median;
  }

  public double getUser() {
    return user;
  }

  public double getSystem() {
    return system;
  }

  public double getMin() {
    return min;
  }

  public double getMax() {
    return max;
  }

  public List<Double> getTimes() {
    return times;
  }

  public List<Integer> getExit_codes() {
    return exit_codes;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }
}
