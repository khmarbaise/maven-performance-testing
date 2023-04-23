package com.soebes.maven.performance;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class SemanticVersion implements Comparable<SemanticVersion> {

  private static final Pattern PATTERN_VERSION = Pattern.compile("(\\d+(.\\d+(.\\d+)?)?)?(-(.*))?");

  private final int[] semVer;
  private final String distro;

  public SemanticVersion(String semVer) {
    if (isNull(semVer)) {
      throw new IllegalArgumentException("Null not allowed.");
    }

    var matcher = PATTERN_VERSION.matcher(semVer);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("No distro and version part existing");
    }

    this.distro = isNull(matcher.group(5)) ? "" : matcher.group(5);


    var split = matcher.group(1).split("\\.");

    if (Arrays.stream(split).allMatch(s -> s.matches("\\d+"))) {
      this.semVer = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
    } else {
      String nonInt = Arrays.stream(split)
          .filter(s -> !s.matches("\\d+"))
          .findFirst()
          .orElseThrow();
      throw new IllegalArgumentException(nonInt + " is not a an int.");
    }
  }

  @Override
  public String toString() {
    var collect = Arrays.stream(this.semVer)
        .boxed()
        .map(Object::toString)
        .collect(Collectors.joining(","));
    return "SemanticVersion{" + collect + '}';
  }

  @Override
  public int compareTo(SemanticVersion o) {
    int result = Arrays.compare(this.semVer, o.semVer);
    return result == 0 ? Integer.compare(this.distro.compareTo(o.distro), 0) : result;
  }
}
