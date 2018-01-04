package com.citi.alan.myproject.tess4j.util;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

@Component
public class TessercatUtil {

    @Value("${tessdata.lib.path}")
    private String datapath;

    public String parseImage(File imageFile) throws IOException, TesseractException {
        long lStartTime = System.currentTimeMillis();
        Tesseract1 instance = new Tesseract1();
        instance.setLanguage("chi_sim+eng");
        instance.setDatapath(datapath);
        System.out.println("--------------------bill detail-------------");
        String result = instance.doOCR(imageFile);
        System.out.println(result);
        long lEndTime = System.currentTimeMillis();
        long output = (lEndTime - lStartTime)/1000;
        System.out.println("parseImage Elapsed time in seconds: " + output);
        return result;
    }
}
