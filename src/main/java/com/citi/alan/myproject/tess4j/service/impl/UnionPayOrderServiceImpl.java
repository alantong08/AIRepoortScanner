package com.citi.alan.myproject.tess4j.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.alan.myproject.tess4j.dao.BlackMerchantDao;
import com.citi.alan.myproject.tess4j.dao.MerchantCategoryCodeDao;
import com.citi.alan.myproject.tess4j.entity.BlackMerchant;
import com.citi.alan.myproject.tess4j.entity.MerchantCategoryCode;
import com.citi.alan.myproject.tess4j.enu.ImageType;
import com.citi.alan.myproject.tess4j.service.api.UnionPayOrderService;
import com.citi.alan.myproject.tess4j.util.ImageUtil;
import com.citi.alan.myproject.tess4j.util.TessercatUtil;
@Service
public class UnionPayOrderServiceImpl implements UnionPayOrderService {
    

    private static Logger logger = Logger.getLogger(UnionPayOrderServiceImpl.class);

    @Autowired
    private TessercatUtil tessercatUtil;
 
    @Autowired 
    private ImageUtil imageUtil;
    
    @Autowired
    private MerchantCategoryCodeDao merchantCategoryCodeDao;
    
    @Autowired
    private BlackMerchantDao blackMerchantDao;

    @Override
    public Map<String, String> parseUnionPayOrder(File file) {
        logger.info("***************start to parseUnionPayOrder()****************");
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            String newFile = imageUtil.processImageThreshold(file.getAbsolutePath(), ImageType.UNIONPAY_SCREENSHOT);
            String result = tessercatUtil.parseImage(new File(newFile));
            String[] list = result.split("\n");
            for (int i = 0; i < list.length; i++) {
                String singleLineResult = list[i];
                if(singleLineResult.contains("商户编号")) {
                    String key = singleLineResult.substring(0, singleLineResult.indexOf("商户编号") + 5);
                    int beginIndex = key.length();
                    String merchantNumber  = singleLineResult.substring(beginIndex, beginIndex+15).trim();
                    logger.info("key: "+key+"\t value:"+merchantNumber); 
                    resultMap = setMccResultForCMB(merchantNumber);

                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return resultMap;
    }

    private Map<String, String> setMccResultForCMB(String merchantNumber) {
        
        Map<String, String> resultMap = new HashMap<String, String>();
        String mcc = merchantNumber.substring(7, 10);
        logger.info("MCC: "+ mcc);
        resultMap.put("商户类别码", mcc);
        resultMap.put("类别名", "暂无纳入");
        
        MerchantCategoryCode merchantCategoryCode = merchantCategoryCodeDao.findByMerchantCode(mcc);
        if(merchantCategoryCode!=null){
            resultMap.put("类别名", merchantCategoryCode.getMerchantCategory());
            if(merchantCategoryCode.isCmbBlackCode()){
                resultMap.put("招商积分情况", "无积分");
            }else{
                BlackMerchant blackMerchant = blackMerchantDao.findByMerchantNum(merchantNumber);
                if(blackMerchant!=null){
                    resultMap.put("招商积分情况", "无积分");
                    resultMap.put("进入招商天书", "是");
                }else{
                    resultMap.put("招商积分情况", "有积分");
                    resultMap.put("进入招商天书", "否");
                } 
            }
        }
        return resultMap;
    }

}
