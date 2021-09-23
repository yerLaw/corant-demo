package org.corant.experiment;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.corant.modules.json.Jsons;
import org.corant.shared.util.Maps;
import org.corant.shared.util.Strings;
import org.corant.shared.util.Texts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/9/1
 * @since
 */
public class TA_DATA_PROCESS_PRO {

  protected static final Logger LOGGER = LoggerFactory.getLogger(TA_DATA_PROCESS_PRO.class);

  public static void main(String[] args) throws Exception {

    LOGGER.info("start...");
    Excels.ExcelStreamlizer refXlsx =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\ref-src.xlsx"));
    Excels.ExcelStreamlizer refXlsx2 =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\ref-src.xlsx"));
    Excels.ExcelStreamlizer refXlsx1 =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\ref-src.xlsx"));
    Excels.ExcelStreamlizer refXlsx3 =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\ref-src.xlsx"));
    Excels.ExcelStreamlizer refXlsx4 =
        Excels.streamlizer(new FileInputStream("E:\\file_file_file\\ref-src.xlsx"));
    List<Map<?, ?>> genArtList = new ArrayList<>();
    List<Map<?, ?>> genArtCriList = new ArrayList<>();
    List<Map<?, ?>> genCriList = new ArrayList<>();
    List<Map<?, ?>> genCriEnumList = new ArrayList<>();
    List<Map<?, ?>> vecMfaSrcList = new ArrayList<>();
    refXlsx.sheetName("GEN-ART").mapStream().skip(1).forEach(genArtList::add);
    refXlsx2.sheetName("GEN-ART-CRI").mapStream().skip(1).forEach(genArtCriList::add);
    refXlsx1.sheetName("GEN-CRI").mapStream().skip(1).forEach(genCriList::add);
    refXlsx3.sheetName("GEN-CRI-ENUM").mapStream().skip(1).forEach(genCriEnumList::add);
    refXlsx4.sheetName("VEC-MFA-SRC").mapStream().skip(1).forEach(vecMfaSrcList::add);
    LOGGER.info("load excel file over...");
    // file found
    File file = new File("E:\\file_file_file\\TecDoc CN.20210824_hero\\parts");
    for (File listFile : Objects.requireNonNull(file.listFiles())) {
      if (listFile.getName().endsWith(".txt")) {
        LOGGER.info("file starts processing :" + listFile.getName());
        Stream<String> lines =
            Texts.lines(
                "E:\\file_file_file\\TecDoc CN.20210824_hero\\parts\\" + listFile.getName());
        List<String> res = new LinkedList<>();
        AtomicInteger i = new AtomicInteger();
        lines.forEach(
            s -> {
              int increment = i.getAndIncrement();
              if (increment % 1000 == 0) {
                LOGGER.info("count : " + increment);
              }
              // category
              Map<Object, Object> map = Jsons.fromString(s);
              String categoryId = (String) map.get("categoryId");
              Optional<Map<?, ?>> optionalMap =
                  genArtList.parallelStream()
                      .filter(genArtMap -> String.valueOf(genArtMap.get("a")).equals(categoryId))
                      .findFirst();
              String refCategoryId = "";
              if (optionalMap.isPresent()) {
                Map<?, ?> findMap = optionalMap.get();
                refCategoryId = (String) findMap.get("b");
                map.put("taCategoryId", refCategoryId);
              }
              // criteria
              List<Map<String, String>> taCriteriaList = new ArrayList<>();
              List<String> criterias = Maps.getMapList(map, "criterias");
              String finalRefCategoryId = refCategoryId;
              criterias.forEach(
                  cri -> {
                    Map<String, String> taCriMap = new HashMap<>();
                    String[] strings = cri.split(":");
                    String cri_key = strings[0];
                    Optional<Map<?, ?>> genArtCriMapOpt =
                        genArtCriList.parallelStream()
                            .filter(
                                genArtCriMap ->
                                    String.valueOf(genArtCriMap.get("a")).equals(finalRefCategoryId)
                                        && cri_key.equals(String.valueOf(genArtCriMap.get("e"))))
                            .findFirst();
                    String criId = "";
                    if (genArtCriMapOpt.isPresent()) {
                      criId = String.valueOf(genArtCriMapOpt.get().get("b"));
                    } else {
                      Optional<Map<?, ?>> genCriMapOpt =
                          genCriList.parallelStream()
                              .filter(
                                  genCriMap -> String.valueOf(genCriMap.get("c")).equals(cri_key))
                              .findFirst();
                      if (genCriMapOpt.isPresent()) {
                        criId = String.valueOf(genCriMapOpt.get().get("b"));
                      }
                    }

                    String cri_value = "";
                    String criEnumValueId = "";
                    if (strings.length >= 2) {
                      cri_value = strings[1];
                      String finalCriId = criId;
                      String finalCri_value = cri_value;
                      Optional<Map<?, ?>> genCriEnumMapOpt =
                          genCriEnumList.parallelStream()
                              .filter(
                                  genCriEnumValueMap ->
                                      String.valueOf(genCriEnumValueMap.get("b")).equals(finalCriId)
                                          && String.valueOf(genCriEnumValueMap.get("g"))
                                              .equals(finalCri_value))
                              .findFirst();
                      if (genCriEnumMapOpt.isPresent()) {
                        criEnumValueId = (String) genCriEnumMapOpt.get().get("e");
                      } else {
                        Optional<Map<?, ?>> enumOptMap =
                            genCriEnumList.parallelStream()
                                .filter(
                                    genCriEnumValueMap ->
                                        String.valueOf(genCriEnumValueMap.get("g"))
                                            .equals(finalCri_value))
                                .findFirst();
                        if (enumOptMap.isPresent()) {
                          criEnumValueId = (String) enumOptMap.get().get("e");
                        }
                      }
                    }
                    taCriMap.put("criId", criId);
                    taCriMap.put("criName", cri_key);
                    taCriMap.put("criEnumValueId", criEnumValueId);
                    taCriMap.put("criEnumValue", cri_value);
                    taCriteriaList.add(taCriMap);
                  });
              map.put("taCriterias", taCriteriaList);
              // OE number
              if (map.get("oeNumbers") instanceof Map) {
                Map<String, Map<String, Object>> oeNumbers = Maps.getMapMap(map, "oeNumbers");
                oeNumbers.forEach(
                    (key, value) -> {
                      String brandName = String.valueOf(value.get("brandName"));
                      Optional<Map<?, ?>> vecMfaMapOpt =
                          vecMfaSrcList.parallelStream()
                              .filter(
                                  vecMfaSrcMap -> {
                                    String zhName =
                                        Strings.remove(String.valueOf(vecMfaSrcMap.get("c")), " ");
                                    String enName =
                                        Strings.remove(String.valueOf(vecMfaSrcMap.get("d")), " ");
                                    String zhName1 =
                                        Strings.remove(String.valueOf(vecMfaSrcMap.get("e")), " ");
                                    String brandNameR = Strings.remove(brandName, " ");
                                    return brandNameR.equals(zhName)
                                        || brandNameR.equals(enName)
                                        || brandNameR.equals(zhName1);
                                  })
                              .findFirst();
                      String brandId = "";
                      if (vecMfaMapOpt.isPresent()) {
                        brandId = String.valueOf(vecMfaMapOpt.get().get("b"));
                      }
                      value.put("brandId", brandId);
                    });
              }

              // vehicleOnedbLinkages
              String attrTaId = "";
              if (map.get("vehicleOnedbLinkages") instanceof Map) {
                Map<String, List<Map<String, Object>>> vehicleOnedbLinkages =
                    Maps.getMapMap(map, "vehicleOnedbLinkages");
                for (Map.Entry<String, List<Map<String, Object>>> entry :
                    vehicleOnedbLinkages.entrySet()) {
                  List<Map<String, Object>> value = entry.getValue();
                  for (Map<String, Object> linkageMap : value) {
                    String attrId = String.valueOf(linkageMap.get("attrId"));
                    Optional<Map<?, ?>> genCriMapOpt =
                        genCriList.parallelStream()
                            .filter(genCriMap -> attrId.equals(String.valueOf(genCriMap.get("a"))))
                            .findFirst();
                    if (genCriMapOpt.isPresent()) {
                      attrTaId = (String) genCriMapOpt.get().get("b");
                    }
                    linkageMap.put("attrTaId", attrTaId);
                  }
                }
              }

              try {
                String valueAsString = Jsons.copyMapper().writeValueAsString(map);
                res.add(valueAsString);
              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
            });
        Texts.tryWriteToFile(
            new File(
                "E:\\file_file_file\\TecDoc CN.20210824_hero\\parts\\pro\\" + listFile.getName()),
            res);
      }
    }
  }
}
