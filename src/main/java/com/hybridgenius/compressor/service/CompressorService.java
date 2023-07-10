package com.hybridgenius.compressor.service;

import com.hybridgenius.compressor.utils.ImageCompressor;
import com.hybridgenius.compressor.utils.PDFCompressor;
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
    public void compressFile(File sourceFilePath) throws DocumentException, IOException {
        String fileExtension = getFileExtension(sourceFilePath);

        switch (fileExtension) {
            case "pdf":
                pdfCompressor.compressPDF(sourceFilePath);
                break;
            case "mp3":
                //method call for mp3 compression
                break;
            case "jpeg":
            case "jpg":
            case "png":
                imageCompressor.compressImage(sourceFilePath, fileExtension);
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


    private static String getFileExtension(File file) {
        String filename = file.getName();
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return null;
        }
        return filename.substring(dotIndex + 1);
    }

}
