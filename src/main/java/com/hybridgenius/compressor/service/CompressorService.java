package com.hybridgenius.compressor.service;

import com.itextpdf.text.DocumentException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class CompressorService {

    @Autowired
    private PDFCompressor pdfCompressor;

    @Autowired
    private ImageCompressor imageCompressor;
    public void compressFile(String sourceFilePath) throws DocumentException, IOException {
        String fileExtension = getFileExtension(sourceFilePath);
        File sourceFile = new File(sourceFilePath);

        switch (fileExtension) {
            case "pdf":
                pdfCompressor.compressPDF(sourceFile);
                break;
            case "mp3":
                //method call for mp3 compression
                break;
            case "jpeg":
            case "jpg":
            case "png":
                imageCompressor.compressImage(sourceFile);
                break;
            case "doc":
            case "docx":
                //method call for doc/docx compression
                break;
            case "wav":
            case "flac":
                //method call for audio compression
                break;
            case "avi":
            case "mp4":
                //method call for video compression
                break;
            default:
                System.out.println("Unsupported file type: " + fileExtension);
        }
    }


    private static String getFileExtension(String filePath) {
        return FilenameUtils.getExtension(filePath);
    }

}
