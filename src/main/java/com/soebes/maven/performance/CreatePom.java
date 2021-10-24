package com.soebes.maven.performance;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.soebes.maven.performance.maven.Plugin;
import com.soebes.maven.performance.maven.Property;
import org.apache.maven.model.Build;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.PluginManagement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CreatePom implements PomBuilder {

  private final Model model;

  private CreatePom(Model model) {
    this.model = model;
  }

  public Model toModel() {
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

  private static Function<Plugin, org.apache.maven.model.Plugin> toPlugin() {
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

  public CreatePom dependencyManagement(Dependency... dependencies) {
    DependencyManagement dependencyManagement = new DependencyManagement();

    Arrays.stream(dependencies).map(toDep()).forEach(s -> dependencyManagement.getDependencies().add(s));
    this.model.setDependencyManagement(dependencyManagement);
    return this;
  }

  public CreatePom dependencies(Dependency... dependencies) {
    Arrays.stream(dependencies).map(toDep()).forEach(s -> this.model.getDependencies().add(s));
    return this;
  }

  /**
   * <pre>
   * CreatePom.of()
   *  .profiles()
   *    .profile()
   *    .profile()
   *  .build()
   *    .defaultGoals()
   *    .plugin()
   *    .pluginManagement()
   * </pre>
   *
   * @return {@link CreatePom}
   */

  public static class CreatePomBuild implements PomBuilder {
    private final Model model;

    private CreatePomBuild(Model model) {
      this.model = model;
    }

    public CreatePomBuild plugins(Plugin... plugins) {
      Arrays.stream(plugins).map(toPlugin()).forEach(s -> this.model.getBuild().getPlugins().add(s));
      return this;
    }

    public CreatePomBuild pluginManagement(Plugin... pluginManagements) {
      return pluginManagement(List.of(pluginManagements));
    }

    public CreatePomBuild pluginManagement(List<Plugin> pluginManagements) {
      PluginManagement pluginManagement = new PluginManagement();
      pluginManagements.stream().map(toPlugin()).forEach(s -> pluginManagement.getPlugins().add(s));
      this.model.getBuild().setPluginManagement(pluginManagement);
      return this;
    }

    public CreatePom modules(String... modules) {
      //TODO: Check for the appropriate packaging type which must be {@code POM}
      Arrays.stream(modules).forEach(s -> this.model.getModules().add(s));
      return new CreatePom(this.model);
    }

    public Model toModel() {
      return model;
    }

  }

  public CreatePomBuild build() {
    Build build = new Build();
    this.model.setBuild(build);
    return new CreatePomBuild(this.model);
  }

  public CreatePom parent(String gav) {
    String[] split = gav.split(":");
    Parent parent = new Parent();
    parent.setGroupId(split[0]);
    parent.setArtifactId(split[1]);
    parent.setVersion(split[2]);
    this.model.setParent(parent);
    return this;
  }

  public CreatePom properties(Property... properties) {
    return properties(Arrays.asList(properties));
  }

  public CreatePom properties(List<Property> properties) {
    properties.stream().forEach(s -> this.model.getProperties().put(s.getKey(), s.getValue()));
    return this;
  }

  public CreatePom modules(String... modules) {
    //TODO: Check for the appropriate packaging type which must be {@code POM}
    Arrays.stream(modules).forEach(s -> this.model.getModules().add(s));
    return this;
  }

  public static CreatePom of(String gav) {
    String[] split = gav.split(":");
    return of(split[0], split[1], split[2]);
  }

  public static CreatePom of(String gav, String packaging) {
    String[] split = gav.split(":");
    return of(split[0], split[1], split[2], packaging);
  }

  public static CreatePom of(String g, String a, String v) {
    return of(g, a, v, null);
  }

  public static CreatePom of(String g, String a, String v, String packaging) {
    Model model = new Model();
    model.setModelVersion("4.0.0");
    model.setGroupId(g);
    model.setArtifactId(a);
    model.setPackaging(packaging);
    model.setVersion(v);
    return new CreatePom(model);
  }
}
