package org.corant.experiment;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.corant.shared.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/7/6
 * @since
 */
public class BMW_OE_MAPPING_7 {
  protected static final Logger LOGGER = LoggerFactory.getLogger(BMW_OE_MAPPING_7.class);

  public static void main(String[] args) throws Exception {
    Excels.ExcelStreamlizer ta_oe_excel =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\BMW_TA_OE_DESC.xlsx"));
    Excels.ExcelStreamlizer bmw_oe_excel =
        Excels.streamlizer(
            new FileInputStream(
                "E:\\file_file_file\\bmw\\bmw_oe\\bmw_oe_mapping_result-39454.xlsx"));
    List<LinkedHashMap<String, Object>> bmwList = new LinkedList<>();
    List<Map<?, ?>> taList = new LinkedList<>();
    bmw_oe_excel.linkedHashMapStream().skip(1).forEach(bmwList::add);
    ta_oe_excel.mapStream().skip(1).forEach(taList::add);
    // excel 输出结果List
    //    List<LinkedHashMap<String, String>> finalList = new LinkedList<>();
    AtomicInteger i = new AtomicInteger();
    bmwList.forEach(
        bmwMap -> {
          int increment = i.getAndIncrement();
          if (increment % 1000 == 0) {
            LOGGER.warn("count : " + increment);
          }
          String bmw_oe_7_number = String.valueOf(bmwMap.get("a"));

          taList.forEach(
              taMap -> {
                String ta_oe_number = String.valueOf(taMap.get("b"));
                if (ta_oe_number.equals(bmw_oe_7_number)) {
                  bmwMap.put("h", taMap.get("c"));
                  bmwMap.put("i", taMap.get("d"));
                }
              });
        });
    FileOutputStream out = new FileOutputStream("E:\\file_file_file\\bmw\\bmw_oe\\bmw_oe_mapping_result_7.xlsx");
    Excels.initWorkbook(new SXSSFWorkbook(), Streams.streamOf(bmwList.iterator()))
        .createSheet()
        .createRowsFrom(0)
        .write(out);
  }
}
