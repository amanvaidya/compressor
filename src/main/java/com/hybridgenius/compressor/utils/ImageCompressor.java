package com.hybridgenius.compressor.utils;


import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



@Service
public class ImageCompressor {

    public void compressJpegImage(File sourceFile, String fileExtension) throws IOException {
        String outputFolderPath = "/Users/amanvaidya/Downloads/output."+fileExtension;
        float compressionQuality = 0.01f; // Adjust the compression quality (0.0 - 1.0)
        try {
            BufferedImage image = ImageIO.read(sourceFile);

            File outputFile = new File(outputFolderPath);

            ImageIO.write(image, fileExtension, outputFile);

            System.out.println("Image compression completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


