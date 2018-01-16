package com.citi.alan.myproject.tess4j.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.alan.myproject.tess4j.service.api.UnionPayOrderService;
import com.citi.alan.myproject.tess4j.util.ImageUtil;
import com.citi.alan.myproject.tess4j.util.TessercatUtil;

import net.sourceforge.tess4j.TesseractException;
@Service
public class UnionPayOrderServiceImpl implements UnionPayOrderService {
    

    private static Logger logger = Logger.getLogger(UnionPayOrderServiceImpl.class);

    @Autowired
    private TessercatUtil tessercatUtil;

    @Autowired 
    private ImageUtil imageUtil;

    @Override
    public Map<String, String> parseUnionPayOrder(File file) {
        logger.info("***************start to parseUnionPayOrder()****************");
        try {
            String newFile = imageUtil.processImageThreshold(file.getAbsolutePath());
            String result = tessercatUtil.parseImage(new File(newFile));
            String[] list = result.split("\n");
            for (int i = 0; i < list.length; i++) {
                String singleLineResult = list[i];
                if(singleLineResult.contains("商户编号")) {
                    String key = singleLineResult.substring(0, singleLineResult.indexOf("商户编号") + 5);
                    int beginIndex = key.length();
                    String merchantNumber  = singleLineResult.substring(beginIndex, beginIndex+15).trim();
                    logger.info("key: "+key+"\t value:"+merchantNumber);
                }
            }
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
        return null;
    }

}