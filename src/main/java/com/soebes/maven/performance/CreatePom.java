package com.soebes.maven.performance;

import org.apache.maven.model.Build;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.PluginManagement;

import java.util.Arrays;
import java.util.function.Function;

public class CreatePom {

  private final Model model;

  private CreatePom(Model model) {
    this.model = model;
  }

  Model toModel() {
    return model;
  }

  private Function<Dependency, org.apache.maven.model.Dependency> toDep() {
    return s -> {
      org.apache.maven.model.Dependency z = new org.apache.maven.model.Dependency();
      z.setGroupId(s.getGroupId());
      z.setArtifactId(s.getArtifactId());
      z.setVersion(s.getVersion());
      return z;
    };
  }

  private Function<Plugin, org.apache.maven.model.Plugin> toPlugin() {
    return s -> {
      org.apache.maven.model.Plugin plugin = new org.apache.maven.model.Plugin();
      plugin.setGroupId(s.getGroupId());
      plugin.setArtifactId(s.getArtifactId());
      plugin.setVersion(s.getVersion());
//      plugin.setGoals();
//      plugin.setDependencies();
//      plugin.setExecutions();
//      plugin.setConfiguration();
      return plugin;
    };
  }

  CreatePom plugins(Plugin... plugins) {
    Arrays.stream(plugins).map(toPlugin()).forEach(s -> this.model.getBuild().getPlugins().add(s));
    return this;
  }


  CreatePom pluginManagement(Plugin... pluginManagements) {
    PluginManagement pluginManagement = new PluginManagement();
    Arrays.stream(pluginManagements).map(toPlugin()).forEach(s -> pluginManagement.getPlugins().add(s));
    this.model.getBuild().setPluginManagement(pluginManagement);
    return this;
  }

  CreatePom dependencyManagement(Dependency... dependencies) {
    DependencyManagement dependencyManagement = new DependencyManagement();

    Arrays.stream(dependencies).map(toDep()).forEach(s -> dependencyManagement.getDependencies().add(s));
    this.model.setDependencyManagement(dependencyManagement);
    return this;
  }

  CreatePom dependencies(Dependency... dependencies) {
    Arrays.stream(dependencies).map(toDep()).forEach(s -> this.model.getDependencies().add(s));
    return this;
  }

  CreatePom build() {
    Build build = new Build();
    this.model.setBuild(build);
    return this;
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

  CreatePom properties(Property... properties) {
    Arrays.stream(properties).forEach(s -> this.model.getProperties().put(s.getKey(), s.getValue()));
    return this;
  }

  CreatePom modules(String... modules) {
    Arrays.stream(modules).forEach(s -> this.model.getModules().add(s));
    return this;
  }

  static CreatePom of(String gav) {
    String[] split = gav.split(":");
    return of(split[0], split[1], split[2]);
  }

  static CreatePom of(String gav, String packaging) {
    String[] split = gav.split(":");
    return of(split[0], split[1], split[2], packaging);
  }

  static CreatePom of(String g, String a, String v) {
    return of(g, a, v, null);
  }

  static CreatePom of(String g, String a, String v, String packaging) {
    Model model = new Model();
    model.setModelVersion("4.0.0");
    model.setGroupId(g);
    model.setArtifactId(a);
    model.setPackaging(packaging);
    model.setVersion(v);
    return new CreatePom(model);
  }
}
