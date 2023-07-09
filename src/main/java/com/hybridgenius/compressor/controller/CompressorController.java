package com.hybridgenius.compressor.controller;

import com.hybridgenius.compressor.service.CompressorService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CompressorController {
    @Autowired
    private CompressorService compressorService;

    @GetMapping("/compress")
    public void doCompression() throws DocumentException, IOException {
        String sourceFilePath = "/Users/amanvaidya/Downloads/output.png";
        compressorService.compressFile(sourceFilePath);
    }

}
