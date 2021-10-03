package com.soebes.duplicate;

import org.xmlbeam.annotation.XBRead;

import java.util.Optional;

public interface Pom {

  @XBRead("/project/name")
  Optional<String> getName();


}