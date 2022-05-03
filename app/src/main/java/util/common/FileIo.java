package cookbook.util.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileIo {

  public static ArrayList<String[]> readFromFileSaveToArrayList(String file) {
    ArrayList<String[]> strArr = new ArrayList<String[]>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
      String line;
      while ((line = reader.readLine()) != null) {
        strArr.add(line.split(";"));
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return strArr;
  }

  public static void main(String[] args) {
    File file = new File("recipes.csv");
    String path = file.getAbsolutePath();
    ArrayList<String[]> mambo = readFromFileSaveToArrayList(path);

    for (String[] m : mambo) {
      System.out.println(m);
    }
  }
}
