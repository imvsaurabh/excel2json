package com.github.imvsaurabh.excel2json.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class Excel2JsonService {

    Map<String, Integer> headerMap = null;

    public void excel2jsonSeatMap(MultipartFile file) {
        try {
            XSSFSheet sheet = init(file);
            DataFormatter formatter = new DataFormatter();
            List<JsonObject> seatMapList = new ArrayList<>();
            sheet.rowIterator().forEachRemaining(row -> {
                if (row.getRowNum() == 0) {
                    log.info("This is header hence skipping it.");
                } else {
                    JsonObject rawJsonObject = new JsonObject();
                    rawJsonObject.addProperty("seatMapName", formatter.formatCellValue(row.getCell(headerMap.get("SeatMapName"))));
                    rawJsonObject.addProperty("seatNumber", formatter.formatCellValue(row.getCell(headerMap.get("SeatNumber"))));
                    rawJsonObject.addProperty("_id", "SEATMAP:" + rawJsonObject.get("seatNumber").getAsString());
                    seatMapList.add(rawJsonObject);
                }
            });
            String json = new Gson().toJson(seatMapList);
            log.info(json);
            writeJsonToFile("SeatMap.json", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void excel2jsonSeatMapSeat(MultipartFile file) {
        try {
            XSSFSheet sheet = init(file);
            DataFormatter formatter = new DataFormatter();
            List<JsonObject> seatMapList = new ArrayList<>();
            sheet.rowIterator().forEachRemaining(row -> {
                if (row.getRowNum() == 0) {
                    log.info("This is header hence skipping it.");
                } else {
                    JsonObject rawJsonObject = new JsonObject();
                    rawJsonObject.addProperty("seatMapName", formatter.formatCellValue(row.getCell(headerMap.get("SeatMapName"))));
                    rawJsonObject.addProperty("seatNumber", formatter.formatCellValue(row.getCell(headerMap.get("SeatNumber"))));
                    rawJsonObject.addProperty("_id", "SEATMAPSEAT:" + rawJsonObject.get("seatNumber").getAsString());
                    rawJsonObject.addProperty("isAllowed", Boolean.valueOf(formatter.formatCellValue(row.getCell(headerMap.get("IsAllowed"))).toLowerCase()));
                    seatMapList.add(rawJsonObject);
                }
            });
            String json = new Gson().toJson(seatMapList);
            log.info(json);
            writeJsonToFile("SeatMapSeat.json", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public XSSFSheet init(MultipartFile file) throws IOException {
        headerMap = new HashMap<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);
        header.cellIterator().forEachRemaining(cell -> {
            headerMap.put(cell.getStringCellValue(), cell.getColumnIndex());
        });
        return sheet;
    }

    public void writeJsonToFile(String fileName, String data) {
        try (FileWriter writer = new FileWriter("src/main/resources/" + fileName)) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
