package org.corant.experiment;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.corant.shared.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/6/24
 * @since
 */
public class BMW_TA_PART_TYPE {
  protected static final Logger LOGGER = LoggerFactory.getLogger(BMW_TA_PART_TYPE.class);

  public static void main(String[] args) throws Exception {
    TextSimilarity similarity = new CosineSimilarity();
    Excels.ExcelStreamlizer bmwStream =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\bmw\\BMW_EPC_所有零件类目.xlsx"));
    Excels.ExcelStreamlizer taStream =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\TA所有零件类目.xlsx"));
    List<Map<?, ?>> bmwList = new LinkedList<>();
    List<Map<?, ?>> taList = new LinkedList<>();
    bmwStream.mapStream().skip(1).forEach(bmwList::add);
    taStream.mapStream().skip(1).forEach(taList::add);
    // excel 输出结果List
    List<LinkedHashMap<String, String>> finalList = new LinkedList<>();

    bmwList.stream()
        .filter(map -> String.valueOf(map.get("c")).equals("P"))
        .forEach(
            bmwMap -> {
              String enName = String.valueOf(bmwMap.get("k"));
              String cnName = String.valueOf(bmwMap.get("l"));
              String text1 = enName + " " + cnName;
              LinkedHashMap<String, String> resultExcelMap = new LinkedHashMap<>();
              resultExcelMap.put("a", String.valueOf(bmwMap.get("a")));
              resultExcelMap.put("b", String.valueOf(bmwMap.get("b")));
              resultExcelMap.put("c", (String) bmwMap.get("k"));
              resultExcelMap.put("d", (String) bmwMap.get("l"));
              AtomicReference<Double> score = new AtomicReference<>((double) 0);
              taList.forEach(
                  taMap -> {
                    String enName1 = String.valueOf(taMap.get("b"));
                    String enName2 = String.valueOf(taMap.get("c"));
                    String zhName = String.valueOf(taMap.get("d"));
                    String text2 = enName1 + " " + zhName;
                    String text3 = enName2 + " " + zhName;
                    double testScore = similarity.getSimilarity(text1, text2);
                    double testScore1 = similarity.getSimilarity(text1, text3);
                    double max = Double.max(testScore, testScore1);
                    if (max > score.get()) {
                      resultExcelMap.put("e", (String) taMap.get("a"));
                      resultExcelMap.put("f", enName1);
                      resultExcelMap.put("g", enName2);
                      resultExcelMap.put("h", zhName);
                      resultExcelMap.put("i", String.valueOf(testScore));
                      if (testScore == max) {
                        LOGGER.warn("text1====" + text1 + ",,,,text2====" + text2);
                      }
                      if (testScore1 == max) {
                        LOGGER.warn("text1====" + text1 + ",,,,text3====" + text3);
                      }
                      score.set(max);
                    }
                  });
              LOGGER.warn("enName========" + enName);
              finalList.add(resultExcelMap);
            });
    FileOutputStream out = new FileOutputStream("E:\\file_file_file\\bmw_ta_part_type.xls");
    Excels.initWorkbook(new XSSFWorkbook(), Streams.streamOf(finalList.iterator()))
        .createSheet()
        .createRowsFrom(0)
        .write(out);
  }
}
