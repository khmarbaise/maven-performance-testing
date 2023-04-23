package com.soebes.maven.performance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class SemanticVersionTest {

  enum Compare {
    GT(">", +1),
    EQ("==", 0),
    LT("<", -1);

    private final String comp;
    private final int compareToValue;

    Compare(String comp, int compareToValue) {
      this.comp = comp;
      this.compareToValue = compareToValue;
    }

    public String getComp() {
      return comp;
    }

    public int compareToValue() {
      return compareToValue;
    }

    public String toString() {
      return this.comp;
    }
  }

  static Stream<Arguments> compareSemanticalVersion() {
    return Stream.of(
        of("0.1.0", "1.0.0", Compare.LT),
        of("00001.0001.0001", "1.1.1", Compare.EQ),
        of("1.0.0", "1.0.0", Compare.EQ),
        of("1.2.0", "1.2.1", Compare.LT),
        of("1.2.10", "1.2.9", Compare.GT),
        of("1.2.0", "1.0.0", Compare.GT),
        of("10.2.0", "10.0.0", Compare.GT),
        of("10.2.0", "10.2.1", Compare.LT),
        of("10.230.47", "10.229.47", Compare.GT),
        of("10.030.47", "10.30.47", Compare.EQ),
        of("10.30.47-open", "10.30.47", Compare.GT),
        of("10.30.47-open", "10.30.47-tem", Compare.LT),
        of("20-open", "20-tem", Compare.LT),
        of("20.3-open", "20.2-open", Compare.GT),
        of("18.ea.33-open", "18.ea.33-open", Compare.EQ)
    );
  }

  @ParameterizedTest(name = "{0} {2} {1}")
  @MethodSource
  void compareSemanticalVersion(String semVer1, String semVer2, Compare expectedResult) {
    var semanticVersion1 = new SemanticVersion(semVer1);
    var semanticVersion2 = new SemanticVersion(semVer2);
    assertThat(semanticVersion1.compareTo(semanticVersion2)).isEqualTo(expectedResult.compareToValue());
  }
  @Test
  void testXX() {
    var semanticVersion1 = new SemanticVersion("18.ea.33-open");
    var semanticVersion2 = new SemanticVersion("18.ea.33-open");
    assertThat(semanticVersion1.compareTo(semanticVersion2)).isZero();
  }

}