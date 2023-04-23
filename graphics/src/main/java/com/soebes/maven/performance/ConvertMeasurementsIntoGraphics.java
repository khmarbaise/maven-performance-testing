package com.soebes.maven.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static com.soebes.maven.performance.Converter.convertFromFileNameToJDKModuleInformation;
import static com.soebes.maven.performance.Converter.readMeasurementFile;

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
    var result = Selector.selectJsonFilesOnly(basepath).stream()
        .map(p -> {
          var mr = convertFromFileNameToJDKModuleInformation(p);
          var measurements = readMeasurementFile(p);
          return new Result(mr.jdk(), mr.numberOfModules(), measurements);
        }).toList();

    return result.stream().filter(s -> s.jdk().equals("20-open")).sorted(Comparator.comparingInt(Result::numberOfModules)).toList();
  }

  public static void main(String[] args) throws IOException {
    var basePath = Path.of("json");
    Path p = Path.of("xindex.html");

    var resultList = x(basePath);
    try (var bufferedWriter = Files.newBufferedWriter(p)) {
      bufferedWriter.write(HTML_START);
      bufferedWriter.newLine();

      bufferedWriter.newLine();
      bufferedWriter.write(HTML_END);
    }
  }
}
