package org.corant.experiment.test;

import java.io.File;
import java.util.Objects;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/8/13
 * @since
 */
public class Test1 {
  public static void main(String[] args) {
    File file = new File("E:\\file_file_file\\TecDoc CN.20210824_hero\\parts");
    for (File listFile : Objects.requireNonNull(file.listFiles())) {
      if (listFile.getName().endsWith(".txt")) {
        System.out.println("listFile.getName() = " + listFile.getName());
      }
    }
  }
}
