package com.seller.quickbuy.QuickBuyApp.utils;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;
 
public class MediaTypeUtils {
 
    // abc.zip
    // abc.pdf,..
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        System.out.println("............"+fileName);
        System.out.println("............"+mineType);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
     
}
