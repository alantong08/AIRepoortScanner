package com.citi.alan.myproject.tess4j.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.citi.alan.myproject.tess4j.dao.BlackMerchantDao;
import com.citi.alan.myproject.tess4j.entity.BlackMerchant;
import com.citi.alan.myproject.tess4j.service.api.BlackMerchantService;

@Service
public class BalckMerchantServiceImpl implements BlackMerchantService {
	private static Logger logger = Logger.getLogger(BalckMerchantServiceImpl.class);
	
	@Autowired
	private BlackMerchantDao  blackMerchantDao;

	public void loadingBlackMerchantList(){
        logger.info("**************start loadingBlackMerchantList()*******************");
        long lStartTime = System.currentTimeMillis();
        try {
            File file = ResourceUtils.getFile("classpath:excel/2018JanBlackList.txt");
            List<String> fileLinesList = FileUtils.readLines(file, "UTF-8");
            logger.info("black merchants total size:" + fileLinesList.size());
            long lEndTime = System.currentTimeMillis();
            long output = lEndTime - lStartTime;
            logger.info("reading black list file  Elapsed time in millseconds: " + output + " ms");

            lStartTime = System.currentTimeMillis();
            List<BlackMerchant> blackMerchants = new ArrayList<>();
            for (String line : fileLinesList) {
                String[] values = line.split(",");
                BlackMerchant blackMerchant = new BlackMerchant();
                blackMerchant.setMerchantNum(values[0]);
                String merchantName = (values.length) > 1 ? values[1] : null;
                blackMerchant.setMerchantName(merchantName);
                blackMerchants.add(blackMerchant);

            }
            blackMerchantDao.batchInsert(blackMerchants);
            lEndTime = System.currentTimeMillis();
            output = lEndTime - lStartTime;
            logger.info("insert record into db  Elapsed time in millseconds: " + output + " ms");
        } catch (Exception e) {
            logger.error(e);
        }

	}


}
