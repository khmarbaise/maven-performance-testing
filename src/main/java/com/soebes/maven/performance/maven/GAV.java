package com.soebes.maven.performance.maven;

public class GAV {
  private final String groupId;
  private final String artifactId;
  private final String version;

  public GAV(String groupId, String artifactId, String version) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
  }

  public static final GAV of(String groupId, String artifactId, String version) {
    return new GAV(groupId, artifactId, version);
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }
}
