package com.hybridgenius.compressor.controller;

import com.hybridgenius.compressor.service.CompressorService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class CompressorController {
    @Autowired
    private CompressorService compressorService;

    @GetMapping("/compress")
    public void doCompression(@RequestParam("file") MultipartFile multipartFile) throws DocumentException, IOException {
        File file = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        compressorService.compressFile(file);
    }

}
