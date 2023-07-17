package com.hybridgenius.compressor.map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ExtensionMap {
    public MediaType getExtensionType(String extension){
        HashMap<String, MediaType> map = new HashMap<>();
        map.put("JPEG",MediaType.IMAGE_JPEG);
        map.put("JPG",MediaType.IMAGE_JPEG);
        map.put("PNG",MediaType.IMAGE_PNG);
        map.put("PDF",MediaType.APPLICATION_PDF);
        map.put("AVI",MediaType.APPLICATION_OCTET_STREAM);
        map.put("MP4",MediaType.APPLICATION_OCTET_STREAM);
        map.put("DOCX",MediaType.parseMediaType("application/msword"));
        map.put("DOC",MediaType.parseMediaType("application/msword"));
        map.put("FLAC",MediaType.parseMediaType("audio/flac"));
        map.put("WAV",MediaType.parseMediaType("audio/wav"));
        return map.get(extension);
    }
}
