package com.soebes.maven.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.soebes.maven.performance.Converter.convertFromFileNameToJDKModuleInformation;
import static com.soebes.maven.performance.Converter.convertToMR;
import static com.soebes.maven.performance.Converter.readMeasurementFile;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class ConvertMeasurementsIntoGraphics {

  private static final String HTML_START = """
      <head>
          <!-- Load plotly.js into the DOM -->
          <script src="https://cdn.plot.ly/plotly-2.20.0.min.js" charset="UTF-8"></script>
      </head>
            
            
      <body>
      <h1>Testing</h1>
      <p>This is an example of an graph:</p>
      <div id="myDiv" style="width: 100%;height: 80%;max-width: 1200px;">
          <!-- Plotly chart will be drawn inside this DIV -->
      </div>
      <p>More on things like this.</p>
      <script>
      """;

  private static final String HTML_END = """
      </script>
      </body>
      """;


  private static List<Result> x(Path basepath) throws IOException {
    return Selector.selectJsonFilesOnly(basepath).stream()
        .map(p -> {
          var mr = convertFromFileNameToJDKModuleInformation(p);
          var measurements  = readMeasurementFile(p);
          return new Result(mr.jdk(), mr.numberOfModules(), measurements);
        }).toList();
  }

  private static final JDK JDK_CONST = new JDK("8.0.362-tem");

  public static void main(String[] args) throws IOException {
    var basePath = Path.of("json");
    Path p = Path.of("xindex.html");

    var resultList = x(basePath);
    List<MR> collect1 = resultList.stream().flatMap(s -> s.JSONMeasurements().getResults().stream().map(convertToMR(new JDK(s.jdk()), s.numberOfModules()))).toList();

    // NoM (NumberOfModules)
    //  JDK         MVN   NoM,
    Map<JDK, Map<MVN, Map<Integer, MR>>> collect = collect1.stream().collect(
        groupingBy(MR::jdk, groupingBy(MR::mvn, toMap(MR::numberOfModules, identity()))));

    var mvnList = collect.get(JDK_CONST).keySet().stream().sorted(Comparator.comparing(MVN::mvn)).toList();

    try (var bw = Files.newBufferedWriter(p)) {
      bw.write(HTML_START);
      bw.newLine();

      bw.write("  var data = [ "); bw.newLine();

      for (MVN mvn : mvnList) {
        Map<Integer, MR> nomMR = collect.get(JDK_CONST).get(mvn);
        List<Map.Entry<Integer, MR>> sortedNomList = nomMR.entrySet().stream().sorted((Comparator.comparingInt(Map.Entry::getKey))).toList();

        bw.write("      {"); bw.newLine();
        String lineNom = sortedNomList.stream().map(Map.Entry::getKey).map(Object::toString).collect(Collectors.joining(",", "x: [", "],"));
        bw.write("         " + lineNom); bw.newLine();
        String lineValues = sortedNomList.stream().map(Map.Entry::getValue).map(s -> Double.toString(s.mean())).collect(Collectors.joining(",", "y: [", "],"));
        bw.write("         " + lineValues); bw.newLine();
        bw.write("         error_y: {"); bw.newLine();
        bw.write("           type: 'data',"); bw.newLine();
        bw.write("                  symmetic: false,"); bw.newLine();
        String errorList = sortedNomList.stream().map(Map.Entry::getValue).map(s -> Double.toString(s.stddev())).collect(Collectors.joining(",", "array: [", "],"));
        bw.write("                  "+ errorList); bw.newLine();
        bw.write("         },         "+ errorList); bw.newLine();
        bw.write("         type: 'scatter',"); bw.newLine();
        bw.write("         name: '%s'".formatted(mvn.mvn())); bw.newLine();
        bw.write("      },"); bw.newLine();
      }

      bw.write("  ];"); bw.newLine();
      bw.write("  var layout = {"); bw.newLine();
      bw.write("      title: '%s',".formatted(JDK_CONST.jdk())); bw.newLine();
      bw.write("      xaxis: {"); bw.newLine();
      bw.write("         title: 'Number of Modules',"); bw.newLine();
      bw.write("         showgrid: true,"); bw.newLine();
      bw.write("         zeroline: true"); bw.newLine();
      bw.write("      },"); bw.newLine();
      bw.write("      yaxis: {"); bw.newLine();
      bw.write("         title: 'Time in seconds',"); bw.newLine();
      bw.write("         showline: true"); bw.newLine();
      bw.write("      },"); bw.newLine();
      bw.write("  };"); bw.newLine();
      bw.write("  Plotly.newPlot('myDiv', data, layout);"); bw.newLine();
      bw.write(HTML_END);
    }
  }
}
