package com.hybridgenius.compressor.service;

import com.hybridgenius.compressor.utils.DocCompressor;
import com.hybridgenius.compressor.utils.ImageCompressor;
import com.hybridgenius.compressor.utils.PDFCompressor;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class CompressorService {

    @Autowired
    private PDFCompressor pdfCompressor;

    @Autowired
    private ImageCompressor imageCompressor;

    @Autowired
    private DocCompressor docCompressor;
    public byte[] compressFile(File sourceFilePath) throws DocumentException, IOException {
        String fileExtension = getFileExtension(sourceFilePath);

        switch (fileExtension) {
            case "pdf":
                return pdfCompressor.compressPDF(sourceFilePath);

            case "mp3":
                //method call for mp3 compression
                break;
            case "jpeg":
            case "jpg":
            case "png":
                return imageCompressor.compressJpegImage(sourceFilePath, fileExtension);
                //break;
            case "doc":
                return docCompressor.compressDoc(sourceFilePath);
            case "docx":
                return docCompressor.compressDocx(sourceFilePath);
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
        return null;
    }


    public String getFileExtension(File file) {
        String filename = file.getName();
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return null;
        }
        return filename.substring(dotIndex + 1);
    }

}
