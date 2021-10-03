package com.soebes.duplicate;

import org.junit.jupiter.api.Test;
import org.xmlbeam.XBProjector;

import java.io.IOException;

public class FirstTest {

  @Test
  void name() throws IOException {
    Pom pom = new XBProjector(XBProjector.Flags.ABSENT_IS_EMPTY).io().file("src/test/resources/first.xml").read(Pom.class);
    System.out.println("pom.getName()=" + pom.getName());
//    for (Pom.Artifact artifact:pom.getDependencies()) {
//      System.out.println("artifact = " + artifact);
//    }

    String name = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML).io().file("src/test/resources/first.xml").evalXPath("/project/name").as(String.class);
    System.out.println("name = " + name);
  }

  @Test
  void second() {
    String name = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML).onXMLString("<project \n" +
        "    xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
        "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "    xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\"><name>test</name></project>").evalXPath("/project/name").as(String.class);
    System.out.println("name = " + name);

  }
}
