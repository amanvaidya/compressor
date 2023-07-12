package com.hybridgenius.compressor.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DocCompressor {
    public void compressDoc(File sourceFile, String fileExtension){
        String outputFilePath = "/Users/amanvaidya/Downloads/output."+fileExtension;
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            HWPFDocument document = new HWPFDocument(fis);

            Range range = document.getRange();
            String content = range.text();

            fis.close();

            FileOutputStream fos = new FileOutputStream(outputFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            ZipEntry entry = new ZipEntry("compressed.doc");
            zos.putNextEntry(entry);

            byte[] data = content.getBytes();
            zos.write(data, 0, data.length);

            zos.closeEntry();
            zos.close();

            System.out.println("Compression completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compressDocx(File sourceFile, String fileExtension) {
        String outputFilePath = "/Users/amanvaidya/Downloads/output." + fileExtension;
        try {
            FileOutputStream fos = new FileOutputStream(outputFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            OPCPackage opcPackage = OPCPackage.open(sourceFile.getPath(), PackageAccess.READ);

            for (PackagePart part : opcPackage.getParts()) {
                ZipEntry entry = new ZipEntry(part.getPartName().toString());
                zos.putNextEntry(entry);

                InputStream inputStream = part.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    zos.write(buffer, 0, bytesRead);
                }
                inputStream.close();

                zos.closeEntry();
            }

            opcPackage.close();
            zos.close();

            System.out.println("Compression completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
