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
public class BMW_OE_MAPPING_LAST_STEP_100_SCORE_FITTING {
  protected static final Logger LOGGER =
      LoggerFactory.getLogger(BMW_OE_MAPPING_LAST_STEP_100_SCORE_FITTING.class);

  public static void main(String[] args) throws Exception {
    LOGGER.info("start...");
    Excels.ExcelStreamlizer oe_category_similarity_excel =
        Excels.streamlizer(
            new FileInputStream("E:\\file_file_file\\bmw\\bmw_oe\\100分零件名称对应表-2020.xlsx"));
    Excels.ExcelStreamlizer bmw_oe_excel =
        Excels.streamlizer(
            new FileInputStream("E:\\file_file_file\\bmw\\bmw_oe\\bmw_part_2020.xlsx"));
    List<LinkedHashMap<String, Object>> bmwList = new LinkedList<>();
    List<Map<?, ?>> oeCategoryMappingList = new LinkedList<>();
    bmw_oe_excel.linkedHashMapStream().skip(1).forEach(bmwList::add);
    oe_category_similarity_excel.mapStream().skip(1).forEach(oeCategoryMappingList::add);
    // excel 输出结果List
    //    List<LinkedHashMap<String, String>> finalList = new LinkedList<>();
    AtomicInteger i = new AtomicInteger();
    bmwList.forEach(
        bmwMap -> {
          int increment = i.getAndIncrement();
          if (increment % 1000 == 0) {
            LOGGER.info("count : " + increment);
          }
          String bmw_hg = String.valueOf(bmwMap.get("b"));
          String bmw_ug = String.valueOf(bmwMap.get("c"));
          String bmw_zh_name = String.valueOf(bmwMap.get("e"));
          String bmw_en_name = String.valueOf(bmwMap.get("d"));
          Optional<Map<?, ?>> optionalMap =
              oeCategoryMappingList.stream()
                  .filter(
                      map ->
                          String.valueOf(map.get("a")).equals(bmw_hg)
                              && String.valueOf(map.get("b")).equals(bmw_ug)
                              && String.valueOf(map.get("c")).equals(bmw_en_name)
                              && String.valueOf(map.get("d")).equals(bmw_zh_name))
                  .findFirst();
          if (optionalMap.isPresent()) {
            Map<?, ?> map = optionalMap.get();
            String gaId = String.valueOf(map.get("g"));
            String gaName = String.valueOf(map.get("h"));
            bmwMap.put("g", gaId);
            bmwMap.put("h", gaName);
          }
        });
    FileOutputStream out =
        new FileOutputStream(
            "E:\\file_file_file\\bmw\\bmw_oe\\2020-bmw_epc中所有零件名称100分对应的所有零件找出来了.xlsx");
    Excels.initWorkbook(new SXSSFWorkbook(), Streams.streamOf(bmwList.iterator()))
        .createSheet()
        .createRowsFrom(0)
        .write(out);
  }
}
