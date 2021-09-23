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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/7/6
 * @since
 */
public class BMW_OE_MAPPING_DESC_TA_DATA {
  protected static final Logger LOGGER = LoggerFactory.getLogger(BMW_OE_MAPPING_DESC_TA_DATA.class);

  public static void main(String[] args) throws Exception {
    Excels.ExcelStreamlizer ta_oe_excel =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\BMW_OE_GA_REF.xlsx"));
    Excels.ExcelStreamlizer bmw_oe_excel =
        Excels.streamlizer(
            new FileInputStream("E:\\file_file_file\\bmw\\bmw_oe\\bmw_ta_oe_100%.xlsx"));
    List<LinkedHashMap<String, Object>> bmwList = new LinkedList<>();
    List<Map<?, ?>> taList = new LinkedList<>();
    bmw_oe_excel.linkedHashMapStream().forEach(bmwList::add);
    ta_oe_excel.mapStream().skip(1).forEach(taList::add);
    //     excel 输出结果List
    List<LinkedHashMap<String, Object>> finalList = new LinkedList<>();
    AtomicInteger i = new AtomicInteger();
    taList.forEach(
        taMap -> {
          int increment = i.getAndIncrement();
          if (increment % 1000 == 0) {
            LOGGER.warn("count : " + increment);
          }

          String ta_oe_number = String.valueOf(taMap.get("b"));

          Optional<LinkedHashMap<String, Object>> optional =
              bmwList.stream()
                  .filter(
                      bmwMap -> {
                        String bmw_oe_number = String.valueOf(bmwMap.get("d"));
                        return bmw_oe_number.equals(ta_oe_number);
                      })
                  .findAny();
          if (optional.isEmpty()) {
            LinkedHashMap<String, Object> resultExcelMap = new LinkedHashMap<>();
            resultExcelMap.put("a", ta_oe_number);
            resultExcelMap.put("b", String.valueOf(taMap.get("c")));
            resultExcelMap.put("c", String.valueOf(taMap.get("d")));
            resultExcelMap.put("d", String.valueOf(taMap.get("e")));
            finalList.add(resultExcelMap);
          }
        });
    FileOutputStream out = new FileOutputStream("E:\\file_file_file\\BMW_TA_OE_DESC.xlsx");
    Excels.initWorkbook(new SXSSFWorkbook(), Streams.streamOf(finalList.iterator()))
        .createSheet()
        .createRowsFrom(0)
        .write(out);
  }
}
