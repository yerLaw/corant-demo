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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/6/15
 * @since
 */
public class BMW_TA_CAR {
  protected static final Logger LOGGER = LoggerFactory.getLogger(BMW_TA_CAR.class);

  public static String epcModelDesignNumberFormat(String modelNumber, String taModelNumber) {
    String[] strings = taModelNumber.split("\\|");
    StringBuilder sb = new StringBuilder();
    if (strings.length == 1) {
      sb.append(String.valueOf(modelNumber).repeat(6));
    } else if (strings.length >= 2) {
      sb.append(String.valueOf(modelNumber).repeat(strings.length));
    }
    return sb.toString();
  }

  public static String taModelDesignNumber(String modelNumber) {
    String[] strings = modelNumber.split("\\|");
    StringBuilder sb = new StringBuilder();
    if (strings.length == 1) {
      sb.append(modelNumber.repeat(6));
    } else if (strings.length >= 2) {
      for (String string : strings) {
        sb.append(string);
      }
    }
    return sb.toString();
  }

  public static String engineCodeFormat(String engineCode, boolean bmw) {
    if (bmw) {
      return engineCode + engineCode + engineCode;
    } else {
      String[] strings = engineCode.split(" ");
      return strings[0] + strings[0] + strings[0];
    }
  }

  public static String bmwTypeNameFormat(String typeName) {
    String res = typeName;
    if (typeName.contains("iX")) {
      res = typeName.replaceAll("iX", "iXdrive");
    } else if (typeName.contains("dX")) {
      res = typeName.replaceAll("dX", "dXdrive");
    }
    return Strings.remove(res, " ");
  }

  public static String dateFormat(String date) {
    String s1 = date.replaceFirst("-", "");
    return s1.replace("-01", "00");
  }

  public static void main(String[] args) {
    try {
      TextSimilarity editDistanceSimilarity = new EditDistanceSimilarity();
      TextSimilarity diceTextSimilarity = new DiceTextSimilarity();
      TextSimilarity jaccardTextSimilarity = new JaccardTextSimilarity();
      TextSimilarity jaroDistanceTextSimilarity = new JaroDistanceTextSimilarity();
      //
      Excels.ExcelStreamlizer bmwStream =
          Excels.streamlizer(new FileInputStream("E:\\file_file_file\\bmw\\bmw_car_2020.xlsx"));
      Excels.ExcelStreamlizer taStream =
          Excels.streamlizer(new FileInputStream("E:\\file_file_file\\bmw\\bmw_car_2020.xlsx"));
      List<Map<?, ?>> bmwList = new LinkedList<>();
      List<Map<?, ?>> taList = new LinkedList<>();
      bmwStream.mapStream().skip(1).forEach(bmwList::add);
      taStream.sheetName("TA").mapStream().skip(1).forEach(taList::add);
      // excel 输出结果List
      List<LinkedHashMap<String, String>> finalList = new LinkedList<>();
      AtomicInteger i = new AtomicInteger();
      taList.forEach(
          map -> {
            int increment = i.getAndIncrement();
            if (increment % 1000 == 0) {
              LOGGER.info("count : " + increment);
            }
            StringBuilder sb = new StringBuilder();
            String typeId = String.valueOf(map.get("a"));
            String modelNumber = String.valueOf(map.get("g"));
            String engineCode = String.valueOf(map.get("h"));
            String typeName = String.valueOf(map.get("i"));
            String startDate = String.valueOf(map.get("d"));
            String endDate = String.valueOf(map.get("e"));
            sb.append(taModelDesignNumber(modelNumber)).append(";");
            sb.append(Strings.remove(typeName, " ")).append(";");
            sb.append("0000").append(dateFormat(startDate)).append(";");
            sb.append("9999").append(dateFormat(endDate));
            String text1 = sb.toString().toLowerCase(Locale.ROOT);
            LinkedHashMap<String, String> resultExcelMap = new LinkedHashMap<>();
            resultExcelMap.put("a", typeId);
            resultExcelMap.put("b", modelNumber);
            resultExcelMap.put("c", engineCode);
            resultExcelMap.put("d", typeName);
            resultExcelMap.put("e", startDate);
            resultExcelMap.put("f", endDate);
            AtomicReference<Double> score = new AtomicReference<>((double) 0);
            String engine = engineCode.split(" ")[0];
            String engine1 = engine + "N";
            String engine2 = engine + "X";
            String engine3 = engine + "Z";
            String engine4 = engine + "N2";
            String engine5 = engine + "S";
            String engine6 = engine + "S1";
            String engine7 = engine + "T";
            String engine8 = engine + "R";
            bmwList.stream()
                .filter(
                    map1 -> {
                      String bmwEngineCode = String.valueOf(map1.get("n"));
                      return bmwEngineCode.equals(engine)
                          || bmwEngineCode.equals(engine1)
                          || bmwEngineCode.equals(engine2)
                          || bmwEngineCode.equals(engine3)
                          || bmwEngineCode.equals(engine4)
                          || bmwEngineCode.equals(engine5)
                          || bmwEngineCode.equals(engine6)
                          || bmwEngineCode.equals(engine7)
                          || bmwEngineCode.equals(engine8);
                    })
                .forEach(
                    bmwMap -> {
                      StringBuilder bmwSb = new StringBuilder();
                      String bmwTypeId = String.valueOf(bmwMap.get("f"));
                      String bmwModelId = String.valueOf(bmwMap.get("a"));
                      String modelSubstring;
                      if (bmwModelId.endsWith("N") || bmwModelId.endsWith("E")) {
                        // F10N --> F10
                        modelSubstring = bmwModelId.substring(0, bmwModelId.length() - 1);
                      } else {
                        modelSubstring = bmwModelId;
                      }
                      String bmwEngineCode = String.valueOf(bmwMap.get("n"));
                      String bmwTypeName = String.valueOf(bmwMap.get("l"));
                      String bmwStartDate = String.valueOf(bmwMap.get("q"));
                      String bmwEndDate = String.valueOf(bmwMap.get("r"));
                      bmwSb
                          .append(epcModelDesignNumberFormat(modelSubstring, modelNumber))
                          .append(";");
                      bmwSb.append(bmwTypeNameFormat(bmwTypeName)).append(";");
                      bmwSb.append("0000").append(dateFormat(bmwStartDate)).append(";");
                      bmwSb.append("9999");
                      if (endDate.equals("NULL")) {
                        bmwSb.append("NULL");
                      } else {
                        bmwSb.append(dateFormat(bmwEndDate));
                      }
                      String text2 = bmwSb.toString().toLowerCase(Locale.ROOT);
                      double editDistanceScore = editDistanceSimilarity.getSimilarity(text1, text2);
                      double diceTextScore = diceTextSimilarity.getSimilarity(text1, text2);
                      double jaccardScore = jaccardTextSimilarity.getSimilarity(text1, text2);
                      double jaroDistanceScore =
                          jaroDistanceTextSimilarity.getSimilarity(text1, text2);
                      double testScore =
                          editDistanceScore + diceTextScore + jaccardScore + jaroDistanceScore;
                      if (testScore > score.get()) {
                        resultExcelMap.put("g", bmwTypeId);
                        resultExcelMap.put("h", bmwModelId);
                        resultExcelMap.put("i", bmwEngineCode);
                        resultExcelMap.put("j", bmwTypeName);
                        resultExcelMap.put("k", bmwStartDate);
                        resultExcelMap.put("l", bmwEndDate);
                        resultExcelMap.put("m", String.valueOf(testScore));
                        resultExcelMap.put("n", text1);
                        resultExcelMap.put("o", text2);
                        score.set(testScore);
                      }
                    });
            finalList.add(resultExcelMap);
          });
      FileOutputStream out = new FileOutputStream("E:\\bmw_ta-v1.2.8.xls");
      Excels.initWorkbook(new XSSFWorkbook(), Streams.streamOf(finalList.iterator()))
          .createSheet()
          .createRowsFrom(0)
          .write(out);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
