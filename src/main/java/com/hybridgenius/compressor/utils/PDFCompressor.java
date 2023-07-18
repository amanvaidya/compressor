package com.hybridgenius.compressor.utils;

import com.itextpdf.text.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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
    public byte[] compressPDF(File sourceFile) throws IOException, DocumentException {

            String compressedPdfPath = "/Users/amanvaidya/Desktop/compressor/images/img";

            try (PDDocument document = PDDocument.load(sourceFile)) {
                PDFRenderer renderer = new PDFRenderer(document);

                for (int pageNumber = 0; pageNumber < document.getNumberOfPages(); pageNumber++) {
                    BufferedImage image = renderer.renderImageWithDPI(pageNumber, 16); // 300 is the DPI (dots per inch)

                    String outputFileName = String.format("image-%d.png", pageNumber + 1);
                    String outputFilePath = compressedPdfPath+outputFileName;

                    ImageIO.write(image, "png", new File(outputFilePath));

                    System.out.println("Page " + (pageNumber + 1) + " converted to image.");
                }
                System.out.println("PDF to image conversion complete.");
                return compress();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

    }

    private byte[] compress(){
        String inputFolderPath = "/Users/amanvaidya/Desktop/compressor/images/";
        String outputFolderPath = "/Users/amanvaidya/Desktop/compressor/images/";
        float compressionQuality = 0.01f; // Adjust the compression quality (0.0 - 1.0)

        File folder = new File(inputFolderPath);
        File[] imageFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                String outputFilePath = outputFolderPath + imageFile.getName();
                compressPNGImage(imageFile.getAbsolutePath(), outputFilePath, compressionQuality);
                System.out.println("Image compressed: " + imageFile.getName());
            }
        }

        System.out.println("Image compression complete.");
        return imageToPdf();
    }

    private static void compressPNGImage(String inputImagePath, String outputImagePath, float compressionQuality) {
        try {
            BufferedImage image = ImageIO.read(new File(inputImagePath));

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No PNG ImageWriter found");
            }
            ImageWriter writer = writers.next();

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionQuality);

            FileImageOutputStream output = new FileImageOutputStream(new File(outputImagePath));
            writer.setOutput(output);
            writer.write(null, new IIOImage(image, null, null), param);

            output.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] imageToPdf(){
        String inputFolderPath = "/Users/amanvaidya/Desktop/compressor/images/";

        try {
            PDDocument document = new PDDocument();

            File folder = new File(inputFolderPath);
            File[] imageFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    BufferedImage image = ImageIO.read(imageFile);
                    PDPage page = new PDPage(new org.apache.pdfbox.pdmodel.common.PDRectangle(image.getWidth(), image.getHeight()));
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.drawImage(PDImageXObject.createFromFileByContent(imageFile, document), 0, 0, image.getWidth(), image.getHeight());
                    contentStream.close();
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();

            System.out.println("Images converted to PDF: " );
            clearData.clearData();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
