package org.corant.experiment;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.corant.shared.util.Lists;
import org.corant.shared.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.DiceTextSimilarity;
import org.xm.similarity.text.EditDistanceSimilarity;
import org.xm.similarity.text.EuclideanDistanceTextSimilarity;
import org.xm.similarity.text.JaccardTextSimilarity;
import org.xm.similarity.text.JaroDistanceTextSimilarity;
import org.xm.similarity.text.ManhattanDistanceTextSimilarity;
import org.xm.similarity.text.SimHashPlusHammingDistanceTextSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * corant <br>
 *
 * @author sushuaihao 2021/7/6
 * @since
 */
public class BMW_OE_MAPPING_GROUP_SCORE {
  protected static final Logger LOGGER = LoggerFactory.getLogger(BMW_OE_MAPPING_GROUP_SCORE.class);

  public static void main(String[] args) throws Exception {
    LOGGER.info("start...");
    TextSimilarity diceTextSimilarity = new DiceTextSimilarity();
    TextSimilarity editDistanceSimilarity = new EditDistanceSimilarity();
    TextSimilarity euclideanDistanceTextSimilarity = new EuclideanDistanceTextSimilarity();
    TextSimilarity jaroDistanceTextSimilarity = new JaroDistanceTextSimilarity();
    TextSimilarity cosineSimilarity = new CosineSimilarity();
    TextSimilarity jaccardTextSimilarity = new JaccardTextSimilarity();
    //    TextSimilarity similarity_6 = new JaroWinklerDistanceTextSimilarity();
    TextSimilarity manhattanDistanceTextSimilarity = new ManhattanDistanceTextSimilarity();
    TextSimilarity simHashPlusHammingDistanceTextSimilarity =
        new SimHashPlusHammingDistanceTextSimilarity();
    Excels.ExcelStreamlizer oe_correct_excel =
        Excels.streamlizer(
            new FileInputStream(
                "E:\\file_file_file\\bmw\\bmw_oe\\important\\宝马OE号所有零件名称所对应TD分类\\宝马OE号在TD完全匹配的所有零件名称(去重)-V8.xlsx"));
    Excels.ExcelStreamlizer bmw_oe_excel =
        Excels.streamlizer(
            new FileInputStream(
                "E:\\file_file_file\\bmw\\bmw_oe\\important\\宝马OE号所有零件名称所对应TD分类\\宝马所有OE号(去重----hg,ug,零件名称有差异的)(无需改进)-2020.xlsx"));
    List<LinkedHashMap<String, Object>> bmwGroupList = new LinkedList<>();
    List<Map<?, ?>> oeCorrectList = new LinkedList<>();
    bmw_oe_excel.linkedHashMapStream().skip(1).forEach(bmwGroupList::add);
    oe_correct_excel.mapStream().skip(1).forEach(oeCorrectList::add);
    // excel 输出结果List
    //    List<LinkedHashMap<String, String>> finalList = new LinkedList<>();
    AtomicInteger i = new AtomicInteger();
    bmwGroupList.forEach(
        bmwMap -> {
          int increment = i.getAndIncrement();
          if (increment % 1000 == 0) {
            LOGGER.info("count : " + increment);
          }
          String bmw_hg = String.valueOf(bmwMap.get("a"));
          String bmw_ug = String.valueOf(bmwMap.get("b"));
          String bmw_en_name = String.valueOf(bmwMap.get("c"));
          String bmw_zh_name = String.valueOf(bmwMap.get("d"));
          String text1 = bmw_en_name.toLowerCase(Locale.ROOT) + " " + bmw_zh_name;
          AtomicReference<Double> score = new AtomicReference<>((double) 0);
          List<Map<?, ?>> maps =
              oeCorrectList.stream()
                  .filter(
                      map ->
                          String.valueOf(map.get("a")).equals(bmw_hg)
                              && String.valueOf(map.get("b")).equals(bmw_ug))
                  .collect(Collectors.toList());
          List<Map<?, ?>> findCountMaxMaps = Lists.newArrayList(maps);
          if (!maps.isEmpty()) {
            for (Map<?, ?> map : maps) {
              String enName = String.valueOf(map.get("c"));
              String zhName = String.valueOf(map.get("d"));
              String text2 =
                  new StringBuilder()
                      .append(enName.toLowerCase(Locale.ROOT))
                      .append(" ")
                      .append(zhName)
                      .toString();
              double diceTextSimilaritySimilarity = diceTextSimilarity.getSimilarity(text1, text2);
              double editDistanceSimilaritySimilarity =
                  editDistanceSimilarity.getSimilarity(text1, text2);
              double euclideanDistanceTextSimilaritySimilarity =
                  euclideanDistanceTextSimilarity.getSimilarity(text1, text2);
              double jaroDistanceTextSimilaritySimilarity =
                  jaroDistanceTextSimilarity.getSimilarity(text1, text2);
              double cosineSimilaritySimilarity = cosineSimilarity.getSimilarity(text1, text2);
              double jaccardTextSimilaritySimilarity =
                  jaccardTextSimilarity.getSimilarity(text1, text2);
              double manhattanDistanceTextSimilaritySimilarity =
                  manhattanDistanceTextSimilarity.getSimilarity(text1, text2);
              double simHashPlusHammingDistanceTextSimilaritySimilarity =
                  simHashPlusHammingDistanceTextSimilarity.getSimilarity(text1, text2);
              double total_score =
                  diceTextSimilaritySimilarity
                      + editDistanceSimilaritySimilarity
                      + euclideanDistanceTextSimilaritySimilarity
                      + jaroDistanceTextSimilaritySimilarity
                      + cosineSimilaritySimilarity
                      + jaccardTextSimilaritySimilarity
                      + manhattanDistanceTextSimilaritySimilarity
                      + simHashPlusHammingDistanceTextSimilaritySimilarity;
              if (diceTextSimilaritySimilarity == 1) {
                selectCountMax(bmwMap, findCountMaxMaps, enName, zhName);
                bmwMap.put("i", 100);
                break;
              }
              if (total_score > score.get()) {
                selectCountMax(bmwMap, findCountMaxMaps, enName, zhName);
                bmwMap.put("i", total_score);
                bmwMap.put("j", diceTextSimilaritySimilarity);
                bmwMap.put("k", editDistanceSimilaritySimilarity);
                bmwMap.put("l", euclideanDistanceTextSimilaritySimilarity);
                bmwMap.put("m", jaroDistanceTextSimilaritySimilarity);
                bmwMap.put("n", cosineSimilaritySimilarity);
                bmwMap.put("o", jaccardTextSimilaritySimilarity);
                bmwMap.put("p", manhattanDistanceTextSimilaritySimilarity);
                bmwMap.put("q", simHashPlusHammingDistanceTextSimilaritySimilarity);
                score.set(total_score);
              }
            }
          }
        });
    FileOutputStream out =
        new FileOutputStream(
            "E:\\file_file_file\\bmw\\bmw_oe\\important\\宝马OE号所有零件名称所对应TD分类\\宝马OE号所有零件名称对应TD分类-V5.xlsx");
    Excels.initWorkbook(new SXSSFWorkbook(), Streams.streamOf(bmwGroupList.iterator()))
        .createSheet()
        .createRowsFrom(0)
        .write(out);
  }

  private static void selectCountMax(
      LinkedHashMap<String, Object> bmwMap,
      List<Map<?, ?>> findCountMaxMaps,
      String enName,
      String zhName) {
    Map<?, ?> countMaxMap =
        findCountMaxMaps.stream()
            .filter(
                map1 ->
                    String.valueOf(map1.get("c")).equals(enName)
                        && String.valueOf(map1.get("d")).equals(zhName))
            .max(
                Comparator.comparingDouble(
                    value -> Double.parseDouble(String.valueOf(value.get("g")))))
            .get();
    bmwMap.put("e", enName);
    bmwMap.put("f", zhName);
    bmwMap.put("g", String.valueOf(countMaxMap.get("e")));
    bmwMap.put("h", String.valueOf(countMaxMap.get("f")));
  }
}
