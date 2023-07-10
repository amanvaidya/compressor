package com.hybridgenius.compressor.utils;

import org.springframework.stereotype.Service;


import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


@Service
public class ImageCompressor {

    public void compressImage(File sourceFile, String fileExtension) throws IOException {
        String outputFolderPath = "/Users/amanvaidya/Downloads/output."+fileExtension;
        float compressionQuality = 0.01f; // Adjust the compression quality (0.0 - 1.0)
        try {
            BufferedImage image = ImageIO.read(sourceFile);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No PNG ImageWriter found");
            }
            ImageWriter writer = writers.next();

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionQuality);

            FileImageOutputStream output = new FileImageOutputStream(new File(outputFolderPath));
            writer.setOutput(output);
            writer.write(null, new IIOImage(image, null, null), param);

            output.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


