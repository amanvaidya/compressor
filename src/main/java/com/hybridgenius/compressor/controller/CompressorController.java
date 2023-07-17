package com.hybridgenius.compressor.controller;

import com.hybridgenius.compressor.map.ExtensionMap;
import com.hybridgenius.compressor.service.CompressorService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class CompressorController {
    @Autowired
    private CompressorService compressorService;
    @Autowired
    private ExtensionMap extensionMap;
    @PostMapping("/compress")
    public ResponseEntity<byte[]> doCompression(@RequestParam("file") MultipartFile multipartFile) throws DocumentException, IOException {
        File file = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        String extension = compressorService.getFileExtension(file);
        MediaType mediaType = extensionMap.getExtensionType(extension.toUpperCase());
        byte[]  data = compressorService.compressFile(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", "data");

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

}
