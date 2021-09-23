package org.corant.experiment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.corant.modules.json.Jsons;
import org.corant.shared.exception.CorantRuntimeException;
import org.corant.shared.util.Encrypts;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.corant.shared.util.Assertions.shouldBeTrue;
import static org.corant.shared.util.Assertions.shouldNotNull;
import static org.corant.shared.util.Empties.isEmpty;
import static org.corant.shared.util.Empties.isNotEmpty;
import static org.corant.shared.util.Maps.mapOf;
import static org.corant.shared.util.Streams.streamOf;

/**
 *
 *
 * @author sushuaihao 2019/5/21
 * @since
 */
public class Excels {

  public static Object parseCellValue(Cell cell) {
    if (cell == null) {
      return null;
    }
    switch (cell.getCellType() == CellType.FORMULA
        ? cell.getCachedFormulaResultType()
        : cell.getCellType()) {
      case NUMERIC:
        return cell.getNumericCellValue();
      case BOOLEAN:
        return cell.getBooleanCellValue();
      case BLANK:
      case ERROR:
        return null;
      default:
        return cell.getStringCellValue();
    }
  }

  public static void map2Row(LinkedHashMap<?, ?> map, Row row) {
    AtomicInteger idx = new AtomicInteger();
    map.forEach((key, value) -> row.createCell(idx.getAndIncrement())
            .setCellValue(value == null ? "" : value.toString()));
  }

  public static Map<String, ?> parseRowValues(Row row) {
    int idx = row.getLastCellNum();
    Map<String, Object> map = new LinkedHashMap<>(idx);
    for (int seq = 0; seq < idx; seq++) {
      map.put(Encrypts.intToAlphabetScale(seq + 1).toLowerCase(), parseCellValue(row.getCell(seq)));
    }
    return map;
  }

  public static LinkedHashMap<String, Object> parseRowValuesX(Row row) {
    int idx = row.getLastCellNum();
    LinkedHashMap<String, Object> map = new LinkedHashMap<>(idx);
    for (int seq = 0; seq < idx; seq++) {
      map.put(Encrypts.intToAlphabetScale(seq + 1).toLowerCase(), parseCellValue(row.getCell(seq)));
    }
    return map;
  }

  public static Map<Integer, ?> parseRowValues(Row row, int... ids) {
    int x = row.getLastCellNum();
    Map<Integer, Object> map = new LinkedHashMap<>();
    if (isEmpty(ids)) {
      for (int seq = 0; seq < x; seq++) {
        map.put(seq, parseCellValue(row.getCell(seq)));
      }
    } else {
      for (int id : ids) {
        if (id > x) {
          map.put(id, null);
        } else {
          map.put(id, parseCellValue(row.getCell(id)));
        }
      }
    }
    return map;
  }

  public static Map<String, ?> parseRowValues(Row row, String... idxs) {
    if (isEmpty(idxs)) {
      return parseRowValues(row);
    } else {
      int[] intIds =
          streamOf(idxs)
              .map(i -> Encrypts.alphabetToIntScale(i) - 1)
              .mapToInt(Integer::intValue)
              .toArray();
      Map<Integer, ?> m = parseRowValues(row, intIds);
      Map<String, Object> map = new LinkedHashMap<>(intIds.length);
      for (int id : intIds) {
        map.put(Encrypts.intToAlphabetScale(id + 1).toLowerCase(), m.get(id));
      }
      return map;
    }
  }

  public static ExcelStreamlizer streamlizer(InputStream is) {
    return ExcelStreamlizer.of(is);
  }

  public static ExcelStreamlizer initWorkbook(Workbook wb, Stream<LinkedHashMap<?, ?>> mapStream) {
    return ExcelStreamlizer.of(wb, mapStream);
  }

  public static List<Map<String, ?>> tryList(InputStream is) {
    try {
      return streamlizer(new BufferedInputStream(is)).sheetIndex(0).stream()
          .skip(0)
          .map(Excels::parseRowValues)
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new CorantRuntimeException(e);
    }
  }

  public static class ExcelStreamlizer {

    InputStream is;
    Predicate<Row> filter = null;
    int sheetIndex = 0;
    String sheetName = null;
    Workbook wb;
    Stream<LinkedHashMap<?, ?>> mapStream;

    ExcelStreamlizer(InputStream is) {
      this.is = shouldNotNull(is);
    }

    ExcelStreamlizer(Workbook wb, Stream<LinkedHashMap<?, ?>> mapStream) {
      this.mapStream = mapStream;
      this.wb = wb;
    }

    static ExcelStreamlizer of(InputStream is) {
      return new ExcelStreamlizer(is);
    }

    static ExcelStreamlizer of(Workbook wb, Stream<LinkedHashMap<?, ?>> mapStream) {
      return new ExcelStreamlizer(wb, mapStream);
    }

    public static ExcelStreamlizer ofXlsx(InputStream is) {
      // TODO FIXME Use XML stream to read.
      return new ExcelStreamlizer(is);
    }

    public ExcelStreamlizer autoSizeColumn(int columnNumber) {
      for (int i = 0; i < columnNumber; i++) {
        getSheet().autoSizeColumn(i);
      }
      return this;
    }

    public ExcelStreamlizer createRowTitle(String... titles) {
      int[] indexArr = {0};
      Row row = getSheet().createRow(0);
      streamOf(titles).forEach(title -> row.createCell(indexArr[0]++).setCellValue(title));
      return this;
    }

    public ExcelStreamlizer createRowsFrom(int index) {
      AtomicInteger idx = new AtomicInteger(index);
      mapStream.forEach(map -> Excels.map2Row(map, getSheet().createRow(idx.getAndIncrement())));
      return this;
    }

    public ExcelStreamlizer createSheet() {
      if (sheetName != null) {
        wb.createSheet(sheetName);
      } else {
        wb.createSheet();
      }
      return this;
    }

    public ExcelStreamlizer filter(Predicate<Row> filter) {
      if (filter != null) {
        this.filter = filter;
      }
      return this;
    }

    public Stream<Map<Integer, ?>> mapStream(int... ids) throws Exception {
      shouldBeTrue(isNotEmpty(ids));
      return stream().map(r -> Excels.parseRowValues(r, ids));
    }

    public Stream<Map<String, ?>> mapStream(String... idxs) throws Exception {
      shouldBeTrue(isNotEmpty(idxs));
      return stream().map(r -> Excels.parseRowValues(r, idxs));
    }

    public Stream<Map<String, ?>> mapStream() throws Exception {
      return stream().map(Excels::parseRowValues);
    }

    public Stream<LinkedHashMap<String, Object>> linkedHashMapStream() throws Exception {
      return stream().map(Excels::parseRowValuesX);
    }

    public <T> Stream<T> objectStream(Class<T> cls, Object... idxPro) throws Exception {
      shouldBeTrue(isNotEmpty(idxPro));
      final Map<Object, Object> mapping = mapOf(idxPro);
      final int size = mapping.size();
      final String[] keys =
          mapping.keySet().stream()
              .filter(Objects::nonNull)
              .map(Object::toString)
              .toArray(String[]::new);
      return stream()
          .map(
              r -> {
                Map<Object, Object> proMap = new LinkedHashMap<>(size);
                parseRowValues(r, keys).entrySet().stream()
                    .filter(e -> mapping.containsKey(e.getKey()))
                    .forEach(e -> proMap.put(mapping.get(e.getKey()), e.getValue()));
                return Jsons.copyMapper().convertValue(proMap, cls);
              });
    }

    public ExcelStreamlizer setRowTitleCellColorOfXSSFWorkbook(Map<Integer, XSSFColor> map) {
      Map<Integer, CellStyle> cellStyleMap = new HashMap<>();
      map.forEach(
          (integer, xssfColor) -> {
            XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
            cellStyle.setFillForegroundColor(xssfColor);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put(integer, cellStyle);
          });
      setRowTitleCellStyle(cellStyleMap);
      return this;
    }

    public ExcelStreamlizer sheetName(String sheetName) {
      this.sheetName = shouldNotNull(sheetName);
      sheetIndex = -1;
      return this;
    }

    public void write(OutputStream stream) throws IOException {
      wb.write(stream);
    }

    void close() {
      if (wb != null) {
        try {
          wb.close();
        } catch (IOException e) {
          // NOOP!
        }
      }
    }

    Sheet getSheet() {
      return sheetName != null ? wb.getSheet(sheetName) : wb.getSheetAt(sheetIndex);
    }

    Sheet sheet() throws Exception {
      wb = WorkbookFactory.create(is);
      return getSheet();
    }

    ExcelStreamlizer sheetIndex(int sheetIndex) {
      this.sheetIndex = sheetIndex;
      sheetName = null;
      return this;
    }

    Stream<Row> stream() throws Exception {
      if (filter == null) {
        return streamOf(sheet().rowIterator()).onClose(this::close);
      } else {
        return streamOf(sheet().rowIterator()).filter(filter).onClose(this::close);
      }
    }

    private ExcelStreamlizer setRowTitleCellStyle(Map<Integer, CellStyle> map) {
      Row row = getSheet().getRow(0);
      for (Map.Entry<Integer, CellStyle> entry : map.entrySet()) {
        Integer integer = entry.getKey();
        CellStyle cellStyle = entry.getValue();
        row.getCell(integer).setCellStyle(cellStyle);
      }
      return this;
    }
  }
}
