package fr.vinted.shipment.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  public static List<String> readResourceFile(String filename) {
    List<String> lineList = new ArrayList<>();
    String line;
    try (InputStream is = FileUtils.class.getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr)) {
      while ((line = br.readLine()) != null) {
        lineList.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lineList;
  }

  public static List<String> readFileSystemFile(String filename) {
    List<String> lineList = new ArrayList<>();
    try {
      lineList = Files.readAllLines(new File(filename).toPath(), Charset.defaultCharset());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lineList;
  }
}
