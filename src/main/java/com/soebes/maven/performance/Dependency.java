package com.soebes.maven.performance;

public class Dependency {
  private String groupId;
  private String artifactId;
  private String version;
  private String classifier;

  public Dependency(String groupId, String artifactId, String version, String classifier) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.classifier = classifier;
  }

  public Dependency(String groupId, String artifactId, String version) {
    this(groupId, artifactId, version, null);
  }

  public Dependency(String groupId, String artifactId) {
    this(groupId, artifactId, null, null);
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

  public String getClassifier() {
    return classifier;
  }
}
