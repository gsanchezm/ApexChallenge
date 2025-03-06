package com.apex.utils;

import com.apex.config.FrameworkException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.apex.config.Constants.WORKBOOKS_FOLDER;

public class ExcelUtil {
    private static Workbook workbook;

    public static void readExcel(String workBookName) {
        File excelFile = new File(String.valueOf(WORKBOOKS_FOLDER.resolve(workBookName)));

        try (FileInputStream fis = new FileInputStream(excelFile)) {
            workbook = WorkbookFactory.create(fis);
        } catch (IOException e) {
            throw new FrameworkException("Error reading Excel file: " + workBookName, e);
        }
    }

    public static Sheet getWorkSheet(String sheetName) {
        return Optional.ofNullable(workbook)
                .map(wb -> wb.getSheet(sheetName))
                .orElseThrow(() -> new FrameworkException("Workbook not loaded or sheet '" + sheetName + "' does not exist"));
    }

    public static List<String> getAllSheetNames() {
        return Optional.ofNullable(workbook)
                .map(wb -> IntStream.range(0, wb.getNumberOfSheets())
                        .mapToObj(wb::getSheetName)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new FrameworkException("Workbook not loaded. Call readExcel() first."));
    }
}

