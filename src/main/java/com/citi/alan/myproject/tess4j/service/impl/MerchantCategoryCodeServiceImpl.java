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

import com.citi.alan.myproject.tess4j.dao.MerchantCategoryCodeDao;
import com.citi.alan.myproject.tess4j.entity.MerchantCategoryCode;
import com.citi.alan.myproject.tess4j.service.api.MerchantCategoryCodeService;

@Service
public class MerchantCategoryCodeServiceImpl implements MerchantCategoryCodeService {
	private static Logger logger = Logger.getLogger(MerchantCategoryCodeServiceImpl.class);
	
	@Autowired
	private MerchantCategoryCodeDao merchantCategoryCodeDao;

	
	public void saveMccDict(){

        logger.info("**************start saveMccDict()*******************");
        long lStartTime = System.currentTimeMillis();
        try {
            File file = ResourceUtils.getFile("classpath:excel/MCC_Dict.txt");
            List<String> fileLinesList = FileUtils.readLines(file, "UTF-8");
            logger.info("merchants total size:" + fileLinesList.size());
            long lEndTime = System.currentTimeMillis();
            long output = lEndTime - lStartTime;
            logger.info("reading list file  Elapsed time in millseconds: " + output + " ms");

            lStartTime = System.currentTimeMillis();
            List<MerchantCategoryCode>  merchantCategoryCodes= new ArrayList<>();
            for (String line : fileLinesList) {
                String[] values = line.split("\t");
                MerchantCategoryCode merchantCategoryCode = new MerchantCategoryCode();
                merchantCategoryCode.setMerchantCode(values[0]);
                merchantCategoryCode.setMerchantCategory(values[1]);
                merchantCategoryCodes.add(merchantCategoryCode);
            }
            merchantCategoryCodeDao.save(merchantCategoryCodes);
            lEndTime = System.currentTimeMillis();
            output = lEndTime - lStartTime;
            logger.info("insert record into db Elapse d time in millseconds: " + output + " ms");
        } catch (Exception e) {
            logger.error(e);
        }
	}
	
     
    public void updateCMBBlackMcc() {

        logger.info("**************start updateMccDict()*******************");

        try {
            File file = ResourceUtils.getFile("classpath:excel/CMBBlackMCC.txt");
            List<String> fileLinesList = FileUtils.readLines(file, "UTF-8");
            logger.info("merchants total size:" + fileLinesList.size());

            List<MerchantCategoryCode> merchantCategoryCodes = new ArrayList<>();
            for (String line : fileLinesList) {
                String[] values = line.split("\t");
                String mcc = values[0];
                MerchantCategoryCode merchantCategoryCode = merchantCategoryCodeDao.findByMerchantCode(mcc);
                if (merchantCategoryCode != null) {
                    merchantCategoryCode.setCmbBlackCode(true);
                    merchantCategoryCodes.add(merchantCategoryCode);
                }
            }
            merchantCategoryCodeDao.save(merchantCategoryCodes);

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
