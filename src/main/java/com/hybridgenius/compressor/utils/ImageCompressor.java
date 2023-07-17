package com.hybridgenius.compressor.utils;


import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;



@Service
public class ImageCompressor {

    public byte[] compressJpegImage(File sourceFile, String fileExtension)  {
        try {
            BufferedImage image = ImageIO.read(sourceFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, fileExtension, outputStream);
            System.out.println("Image compression completed.");
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}


