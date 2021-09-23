package org.corant.experiment;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.corant.shared.util.Streams;
import org.corant.shared.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xm.similarity.text.DiceTextSimilarity;
import org.xm.similarity.text.EditDistanceSimilarity;
import org.xm.similarity.text.JaccardTextSimilarity;
import org.xm.similarity.text.JaroDistanceTextSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.corant.experiment.BMW_TA_CAR.bmwTypeNameFormat;
import static org.corant.experiment.BMW_TA_CAR.epcModelDesignNumberFormat;
import static org.corant.experiment.BMW_TA_CAR.taModelDesignNumber;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/6/15
 * @since
 */
public class BMW_TA_CAR_SIMILARITY_SCORE {
  protected static final Logger LOGGER = LoggerFactory.getLogger(BMW_TA_CAR_SIMILARITY_SCORE.class);

  static Object[] idxPro = {
    "a", "a",
    "b", "b",
    "c", "c",
    "d", "d",
    "e", "e",
    "f", "f",
    "g", "g",
    "h", "h",
    "i", "i",
    "j", "j",
    "k", "k",
    "l", "l"
  };

  public static void main(String[] args) {
    try {
      TextSimilarity editDistanceSimilarity = new EditDistanceSimilarity();
      TextSimilarity diceTextSimilarity = new DiceTextSimilarity();
      TextSimilarity jaccardTextSimilarity = new JaccardTextSimilarity();
      TextSimilarity jaroDistanceTextSimilarity = new JaroDistanceTextSimilarity();
      //
      Excels.ExcelStreamlizer resStream =
          Excels.streamlizer(new FileInputStream("E:\\file_file_file\\bmw\\bmw_ta-v1.2.5.xls"));
      List<LinkedHashMap<String, Object>> resList = new LinkedList<>();
      resStream.objectStream(LinkedHashMap.class, idxPro).forEach(resList::add);
      // excel 输出结果List
      List<LinkedHashMap<String, Object>> finalList = new LinkedList<>();
      resList.forEach(
          map -> {
            String taModel = String.valueOf(map.get("b"));
            String bmwModel = String.valueOf(map.get("h"));
            String taTypeName = String.valueOf(map.get("d"));
            String bmwTypeName = String.valueOf(map.get("j"));
            String modelSubstring;
            if (bmwModel.endsWith("N") || bmwModel.endsWith("E")) {
              // F10N --> F10
              modelSubstring = bmwModel.substring(0, bmwModel.length() - 1);
            } else {
              modelSubstring = bmwModel;
            }
            String text2 =
                new StringBuilder()
                    .append(epcModelDesignNumberFormat(modelSubstring, taModel))
                    .append(" ")
                    .append(bmwTypeNameFormat(bmwTypeName))
                    .toString()
                    .toLowerCase(Locale.ROOT);
            String text1 =
                new StringBuilder()
                    .append(taModelDesignNumber(taModel))
                    .append(" ")
                    .append(Strings.remove(taTypeName, " "))
                    .toString()
                    .toLowerCase(Locale.ROOT);
            double score =
                editDistanceSimilarity.getSimilarity(text1, text2)
                    + diceTextSimilarity.getSimilarity(text1, text2)
                    + jaccardTextSimilarity.getSimilarity(text1, text2)
                    + jaroDistanceTextSimilarity.getSimilarity(text1, text2);
            map.put("m", score);
            map.put("n", text1);
            map.put("o", text2);
            finalList.add(map);
          });

      FileOutputStream out = new FileOutputStream("E:\\file_file_file\\bmw\\bmw_tax-v1.2.5.2.xls");
      Excels.initWorkbook(new XSSFWorkbook(), Streams.streamOf(finalList.iterator()))
          .createSheet()
          .createRowsFrom(0)
          .write(out);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
