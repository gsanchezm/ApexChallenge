package com.apex.utils.data;

import com.apex.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class ExcelArrayData extends ExcelUtil implements IGetTableArray, IGetTwoDimensionalArray {
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    @Override
    public Object[][] asTwoDimensionalArray(List<LinkedHashMap<Object, Object>> interimResults) {
        return interimResults.stream()
                .map(result -> new Object[]{result})
                .toArray(Object[][]::new);
    }

    private static LinkedHashMap<Object, Object> transform(List<String> names, List<String> values) {
        return IntStream.range(0, Math.min(names.size(), values.size()))
                .boxed()
                .collect(Collectors.toMap(names::get, values::get, (existing, replacement) -> existing, LinkedHashMap::new));
    }

    private static List<String> getValuesInEachRow(Row row) {
        return StreamSupport.stream(row.spliterator(), false)
                .map(cell -> cell.toString().equalsIgnoreCase("empty") ? "" : DATA_FORMATTER.formatCellValue(cell))
                .collect(Collectors.toList());
    }

    @Override
    public Object[][] getTableArray(String excelWorkbook, String excelWorkSheet) {
        try {
            readExcel(excelWorkbook);
            Sheet sheet = getWorkSheet(excelWorkSheet);

            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) {
                throw new IllegalStateException("The sheet is empty.");
            }

            List<LinkedHashMap<Object, Object>> results = new ArrayList<>();
            List<String> keys = getValuesInEachRow(rowIterator.next());

            rowIterator.forEachRemaining(row -> results.add(transform(keys, getValuesInEachRow(row))));

            return asTwoDimensionalArray(results);
        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file: " + excelWorkbook, e);
        }
    }
}
