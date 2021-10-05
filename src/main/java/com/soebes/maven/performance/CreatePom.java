package com.soebes.maven.performance;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;

import java.util.Arrays;
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

  CreatePom parent(String gav) {
    String[] split = gav.split(":");
    Parent parent = new Parent();
    parent.setGroupId(split[0]);
    parent.setArtifactId(split[1]);
    parent.setVersion(split[2]);
    this.model.setParent(parent);
    return this;
  }

  CreatePom properties(Property... modules) {
    return new CreatePom(null);
  }

  CreatePom modules(String... modules) {
    Arrays.stream(modules).forEach(s -> this.model.getModules().add(s));
    return this;
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
