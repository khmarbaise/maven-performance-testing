#!/usr/bin/env groovy

class PomFile {
  def pluginList = [
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-clean-plugin',
      'version' : '3.1.0',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-resources-plugin',
      'version' : '3.1.0',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-compiler-plugin',
      'version' : '3.8.1',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-jar-plugin',
      'version' : '3.2.0',
      'configuration' : [ 'forceCreation':'true' ]
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-install-plugin',
      'version' : '2.5.2',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-deploy-plugin',
      'version' : '2.8.2',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-surefire-plugin',
      'version' : '2.22.2',
    ],
    [
      'groupId' : 'org.apache.maven.plugins',
      'artifactId' : 'maven-failsafe-plugin',
      'version' : '2.22.2',
    ],
  ]
  String groupId
  String artifactId
  String version
  File folder

  PomFile (File folder, String groupId, String artifactId, String version) {
    this.folder = folder
    this.groupId = groupId
    this.artifactId = artifactId
    this.version = version
  }

  def writePomFile (Map parentPar) {
    writePomFile(parentPar, null)
  }
  def writePomFile () {
    writePomFile(null, null)
  }
  def writePomFile (Map parentPar, modulesPar) {
    new File(this.folder,'pom.xml').withWriter('utf-8') { writer ->
        def builder = new groovy.xml.MarkupBuilder(writer)
        builder.getMkp().xmlDeclaration( 'version':'1.0', 'encoding':'UTF-8')
        builder.project ( 
              'xmlns':"http://maven.apache.org/POM/4.0.0",
              'xmlns:xsi':"http://www.w3.org/2001/XMLSchema-instance",
              'xsi:schemaLocation':"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
            ) {
            modelVersion("4.0.0")
            if (parentPar != null) { 
              parent {
                groupId(parentPar['groupId'])
                artifactId(parentPar['artifactId'])
                version(this.version)
              }
            }
            groupId(this.groupId)
            artifactId(this.artifactId)
            if (parentPar == null) {
              version(this.version)
            }

            if (modulesPar != null) {
              packaging('pom')
              modules {
                modulesPar.each {
                  module(it)
                }
              }
              properties {
                'project.build.sourceEncoding'('UTF-8')
                'maven.compiler.source'('1.7')
                'maven.compiler.target'('1.7')
              }
              'build' {
                pluginManagement {
                    plugins {
                        pluginList.each { value ->
                            plugin {
                                groupId(value['groupId'])
                                artifactId(value['artifactId'])
                                version(value['version'])
                                //println "gav: ${value['groupId']}:${value['artifactId']}:${value['version']}  ${value['configuration']}"
                                if (value.containsKey('configuration')) {
                                    def configItems = value['configuration']
                                    //println "items: ${configItems}"
                                    configuration {
                                        configItems.each { configItem, configValue ->
                                          //println " -> ${configItem} ${configValue}"
                                          "${configItem}"("${configValue}")
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
              }
            }
        }
    }    
  }
}

class JavaFile {
  File baseFolder
  JavaFile (File baseFolder ) {
    this.baseFolder = baseFolder
  }

  def writeJavaFile () {
    def javaFile = new File (this.baseFolder, "src/main/java/com/soebes")
    javaFile.mkdirs() 
    new File(javaFile,'FirstJava.java').withWriter('utf-8') { writer ->
      writer.writeLine('package com.soebes;')
      writer.writeLine('public class FirstJava {')
      writer.writeLine('  public FirstJava() {')
      writer.writeLine('  }')
      writer.writeLine('}')
    }
  }
}

def folder = new File (".", "reactor")

if ( folder.exists() ) {
  folder.deleteDir()
}
folder.mkdirs()

def levelList = []
(1..20).each { level ->

  def levelFormat = sprintf ("%04d", level)
  def levelModuleName = 'module-' + levelFormat
  println "Level: ${levelModuleName}"

  levelFolder = new File (folder, levelModuleName);
  levelFolder.mkdirs()

  PomFile pf = new PomFile (levelFolder, "org.test.level", levelModuleName, "1.0.0-SNAPSHOT")

  pf.writePomFile(['groupId':'org.test.parent', 'artifactId':'reactor-parent'])

  new JavaFile(levelFolder).writeJavaFile()

  levelList << levelModuleName 

}

pf = new PomFile (folder, "org.test.parent", "reactor-parent", "1.0.0-SNAPSHOT")

pf.writePomFile( null, levelList)

