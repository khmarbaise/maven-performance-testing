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
      bw.write("         title: 'Year',"); bw.newLine();
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
    /*
    var data = [
        {
            x: [10,                 20,            50,            100,           200,                500,                1000,          2000,               2200,          2500,               3000,          4000,          5000],
            y: [0.7058693520000001, 0.70326687898, 0.75888358718, 0.83140606098, 0.9651062595200003, 1.2613795534999999, 1.71636063838, 2.8701383735999997, 3.14558962048, 3.5709097568000003, 4.44405297286, 6.37712811444, 8.92096394044],
            // error_y: {
            //     type: 'data',
            //     array: [0.01965457878939904, 0.021414681030976355, 0.017865813231921372, 0.015592674878892671, 0.020700311773988643, 0.023408934935421767, 0.020508491781688076, 0.01674979372029958, 0.02037036413141284, 0.027684828073751715, 0.05058754138604269, 0.04950750593966928, 0.05539394146462462, 0.3209943034622496],
            // },
            type: 'scatter',
            name: 'MVN 3.0.5'
        },
    ];
    var layout = {
        title: 'open JDK 20',
        xaxis: {
            title: 'Year',
            showgrid: true,
            zeroline: true
        },
        yaxis: {
            title: 'Time in seconds',
            showline: true
        }
    };
    Plotly.newPlot('myDiv', data, layout);

     */
  }
}
