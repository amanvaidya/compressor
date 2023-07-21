package com.hybridgenius.compressor.utils;

import com.itextpdf.text.*;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

@Service
public class PDFCompressor {

    @Autowired
    private ClearData clearData;

    public byte[] compressPDF(File sourceFile){
        try (PDDocument document = PDDocument.load(sourceFile)) {
            for (PDPage page : document.getPages()) {
                compressImages(document, page);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();
            System.out.println("PDF compression complete.");
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static void compressImages(PDDocument document, PDPage page) throws IOException {
        PDResources resources = page.getResources();
        if (resources != null) {
            for (COSName name : resources.getXObjectNames()) {
                if (resources.isImageXObject(name)) {
                    PDImageXObject image = (PDImageXObject) resources.getXObject(name);
                    if (image != null && "image/jpeg".equals(image.getSuffix())) {
                        PDImageXObject compressedImage = JPEGFactory.createFromImage(document, image.getImage(), 0.5f);
                        resources.put(name, compressedImage);
                    }
                }
            }
        }
    }
}
