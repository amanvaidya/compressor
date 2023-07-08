package com.hybridgenius.compressor.utils;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ClearData {
    public void clearData(){
        String folderPath = "/Users/amanvaidya/Desktop/compressor/images/";

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("File deleted: " + file.getName());
                    } else {
                        System.out.println("Failed to delete file: " + file.getName());
                    }
                }
            }
        }

        System.out.println("File deletion complete.");
    }
}
