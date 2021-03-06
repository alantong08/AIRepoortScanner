package com.citi.alan.myproject.tess4j.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.service.api.UnionPayOrderService;
import com.citi.alan.myproject.tess4j.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RequestMapping("/unionpay")
@RestController
public class UnionPayController {
	
	private static Logger logger = Logger.getLogger(UnionPayController.class);

    @Value("${unionpay.upload.file.path}")
    private String uploadFilePath;

    private final static String DATE_PATTERN = "yyyyMMddHHmmssSSS";

    @Autowired
    private UnionPayOrderService unionPayOrderService;
	
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody  
    public ModelAndView handleUploadFile(HttpServletRequest request) throws JsonProcessingException {
          
        logger.info("*****start to handleUploadFile*******");
        BillOrderDetail detail = null;
        String viewName = "";  
        try {
            MultipartFile multipart = ((MultipartHttpServletRequest) request).getFile("file-order");

            String originalFileName = multipart.getOriginalFilename();  
            if (multipart != null && originalFileName != null && originalFileName .length() > 0) {
                String newFileName = DateUtil.getFormatDateStr(DATE_PATTERN) + originalFileName.substring(originalFileName.lastIndexOf("."));
                File newFile = new File(uploadFilePath + newFileName);
                multipart.transferTo(newFile);
                unionPayOrderService.parseUnionPayOrder(newFile);
               // detail = billOrderDetectorService.detetctBillOrderDetail(newFile, activityType, user);
                viewName="weui-confirm";
            } 
        } catch (Exception se) {
            logger.error(se);
            viewName="weui-500";
        }
        ModelAndView mView = new ModelAndView(viewName);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(detail);
        mView.addObject("billDetail",json);
        return mView;
    }



}
