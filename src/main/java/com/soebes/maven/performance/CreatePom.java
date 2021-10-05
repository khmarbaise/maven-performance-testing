package com.soebes.maven.performance;

import org.apache.maven.model.Model;

import java.util.List;

public class CreatePom {

  private final Model model;

  private CreatePom(Model model) {
    this.model = model;
  }

  Model toModel() {
    return model;
  }

  static CreatePom plugins(List<Plugin> plugins) {
    return new CreatePom(null);
  }

  static CreatePom pluginManagement(List<Plugin> pluginManagements) {
    return new CreatePom(null);
  }

  static CreatePom dependencyManagement(List<Dependency> dependencies) {
    return new CreatePom(null);
  }

  static CreatePom dependencies(Dependency... dependencies) {
    return new CreatePom(null);
  }

  static CreatePom dependencies(List<Dependency> dependencies) {
    return new CreatePom(null);
  }

  static CreatePom dependencies() {
    return new CreatePom(null);
  }

  static CreatePom build() {
    return new CreatePom(null);
  }

  static CreatePom parent(String gav) {
    return new CreatePom(null);
  }

  static CreatePom properties(Property... modules) {
    return new CreatePom(null);
  }

  static CreatePom modules(Module... modules) {
    return new CreatePom(null);
  }

  static CreatePom of(String gav) {
    String[] split = gav.split(":");
    Model model = new Model();
    model.setModelVersion("4.0.0");
    model.setGroupId(split[0]);
    model.setArtifactId(split[1]);
    model.setVersion(split[2]);
    return new CreatePom(model);
  }

  static CreatePom of(String g, String a, String v) {
    Model model = new Model();
    model.setGroupId(g);
    model.setArtifactId(a);
    model.setVersion(v);
    return new CreatePom(model);
  }
}
