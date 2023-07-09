package com.hybridgenius.compressor.utils;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class ImageCompressor {

    public void compressImage(File sourceFile) throws IOException {
        String inputFolderPath = "/Users/amanvaidya/Downloads/Myproject1.png";
        String outputFolderPath = "/Users/amanvaidya/Downloads/output.png";
        BufferedImage image = ImageIO.read(new File(inputFolderPath));
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

        ImageWriteParam writeParam = new JPEGImageWriteParam(null);
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(0.5f); // Set the compression quality (0.0 - 1.0)

        File outputFile = new File(outputFolderPath);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        writer.setOutput(ImageIO.createImageOutputStream(outputStream));

        writer.write(null, new javax.imageio.IIOImage(image, null, null), writeParam);

        writer.dispose();
        outputStream.close();

        System.out.println("Image compression complete.");
    }
}
