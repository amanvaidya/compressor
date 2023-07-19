package com.hybridgenius.compressor.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DocCompressor {
    public byte[] compressDoc(File sourceFile){
        String outputFileName = sourceFile.getName();
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            HWPFDocument document = new HWPFDocument(fis);

            Range range = document.getRange();
            String content = range.text();

            fis.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            ZipEntry entry = new ZipEntry(outputFileName);
            zos.putNextEntry(entry);

            byte[] data = content.getBytes();
            zos.write(data, 0, data.length);

            zos.closeEntry();
            zos.close();

            System.out.println("Compression completed successfully.");

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] compressDocx(File sourceFile) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

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

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
