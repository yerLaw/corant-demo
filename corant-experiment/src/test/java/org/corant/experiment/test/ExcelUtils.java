package org.corant.experiment.test;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.corant.experiment.Excels;
import org.corant.shared.util.Streams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/6/17
 * @since
 */
public class ExcelUtils {
  public static void main(String[] args) throws IOException {
    List<LinkedHashMap<String, String>> finalList = new LinkedList<>();

    for (int i = 0; i < 100; i++) {
      LinkedHashMap<String, String> map = new LinkedHashMap<>();
      map.put("a", "1");
      map.put("b", "2");
      map.put("c", "3");
      if (i != 10) {
        map.put("d", "5");
        map.put("e", "5");
        map.put("j", "5");
      }
      finalList.add(map);
      //
    }

    FileOutputStream out = new FileOutputStream("E:\\bmw_ta.xls");
    Excels.initWorkbook(new XSSFWorkbook(), Streams.streamOf(finalList.iterator()))
        .createSheet()
        .createRowsFrom(0)
        .write(out);
  }
}
