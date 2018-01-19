package com.citi.alan.myproject.tess4j.util;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

@Component
public class TessercatUtil {

	private static Logger logger = Logger.getLogger(TessercatUtil.class);
	 
    @Value("${tessdata.lib.path}")
    private String datapath;
    
    private Tesseract1 instance = null;
    
    @PostConstruct
    public void initTesseractInstance() {
    		instance = new Tesseract1();
        instance.setLanguage("chi_sim+eng");
        instance.setDatapath(datapath);
    }
   
    public synchronized String parseImage(File imageFile) throws IOException, TesseractException {
        long lStartTime = System.currentTimeMillis();
        logger.info("--------------------bill detail-------------");
        String result = instance.doOCR(imageFile);
        logger.info(result);
        long lEndTime = System.currentTimeMillis();
        long output = (lEndTime - lStartTime)/1000;
        logger.info("parseImage Elapsed time in seconds: " + output);
        return result;
    }
}
