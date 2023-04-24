package com.soebes.maven.performance;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class SemanticVersion implements Comparable<SemanticVersion> {

  private static final Pattern PATTERN_VERSION = Pattern.compile("(\\d+(.[0-9a-zA-Z]+(.[0-9a-zA-Z]+)?)?)?(-(.*))?");

  private final Object[] semVer;
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

    this.semVer = new Object[3];
    for (int i = 0; i < split.length; i++) {
      if (split[i].matches("\\d+")) {
        this.semVer[i] = Integer.valueOf(split[i]);
      } else {
        this.semVer[i] = split[i];
      }
    }
  }

  @Override
  public String toString() {
    var collect = Arrays.stream(this.semVer)
        .map(Object::toString)
        .collect(Collectors.joining(","));
    return "SemanticVersion{" + collect + '}';
  }

  @Override
  public int compareTo(SemanticVersion o) {
///    int result = Arrays.compare(this.semVer, o.semVer);
//    return result == 0 ? Integer.compare(this.distro.compareTo(o.distro), 0) : result;
    return 0;
  }
}
