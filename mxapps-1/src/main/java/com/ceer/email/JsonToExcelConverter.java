package com.ceer.email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonToExcelConverter {
    public static File convertJsonToExcel(String json, String filePath) {
        // Convert JSON string to a List of Objects
        Gson gson = new Gson();
        List<JsonDataFormat> dataList = gson.fromJson(json, new TypeToken<List<JsonDataFormat>>(){}.getType());

        // Create an Excel workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("User ID");
        headerRow.createCell(1).setCellValue("ID");
        headerRow.createCell(2).setCellValue("Title");
        headerRow.createCell(3).setCellValue("Body");

        // Fill data rows
        int rowNum = 1;
        for (JsonDataFormat data : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getUserId());
            row.createCell(1).setCellValue(data.getId());
            row.createCell(2).setCellValue(data.getTitle());
            row.createCell(3).setCellValue(data.getBody());
        }

        File excelFile = new File(filePath);
        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
            workbook.write(fileOut);
            System.out.println("Excel file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return excelFile;
    }
}

