package com.soebes.maven.performance;

public class Plugin {
  private String groupId;
  private String artifactId;
  private String version;

  public static Plugin of(String groupId, String artifactId, String version) {
    return new Plugin(groupId, artifactId, version);
  }

  public Plugin(String groupId, String artifactId, String version) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
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
