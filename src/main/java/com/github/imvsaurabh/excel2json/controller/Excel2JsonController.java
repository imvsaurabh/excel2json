package com.github.imvsaurabh.excel2json.controller;

import com.github.imvsaurabh.excel2json.service.Excel2JsonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/excel2json")
public class Excel2JsonController {

    private Excel2JsonService service;

    public Excel2JsonController(Excel2JsonService service) {
        this.service = service;
    }

    @PostMapping("/excel")
    public ResponseEntity<String> uploadExcelToConvert(@RequestParam("files") MultipartFile[] files) {
        Arrays.asList(files).forEach(file -> {
            if (Objects.equals(file.getOriginalFilename(), "SeatMap.xlsx"))
                service.excel2jsonSeatMap(file);
            if (Objects.equals(file.getOriginalFilename(), "SeatMapSeat.xlsx"))
                service.excel2jsonSeatMapSeat(file);
        });
        return ResponseEntity.ok("File converted to json successfully.");
    }
}
