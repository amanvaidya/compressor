package com.hybridgenius.compressor.utils;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.springframework.stereotype.Service;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


@Service
public class ImageCompressor {

    public void compressImage(File sourceFile) throws IOException {
        String inputFolderPath = "/Users/amanvaidya/Downloads/backenddevelopment.png";
        String outputFolderPath = "/Users/amanvaidya/Downloads/output.png";
        float compressionQuality = 0.01f; // Adjust the compression quality (0.0 - 1.0)
        try {
            BufferedImage image = ImageIO.read(new File(inputFolderPath));

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


