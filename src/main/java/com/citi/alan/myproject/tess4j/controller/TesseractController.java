package com.citi.alan.myproject.tess4j.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.citi.alan.myproject.tess4j.entity.Merchant;
import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.model.ResponseResult;
import com.citi.alan.myproject.tess4j.service.api.BillOrderDetectorService;
import com.citi.alan.myproject.tess4j.service.api.MerchantService;
import com.citi.alan.myproject.tess4j.service.api.UserInfoService;
import com.citi.alan.myproject.tess4j.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class TesseractController {

	@Value("${upload.file.path}")
	private String uploadFilePath;

	private final static String DATE_PATTERN = "yyyyMMddHHmmssSSS";

	@Autowired
	private BillOrderDetectorService billOrderDetectorService; 

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MerchantService merchantService;
	

	@RequestMapping(value = "/tess4j/submit", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView handleUploadFile(HttpServletRequest request) throws JsonProcessingException {
		BillOrderDetail detail = null;
		String viewName = "";
		try {
			String activityType = request.getParameter("activityType");
			MultipartFile multipart = ((MultipartHttpServletRequest) request).getFile("file-order");
			String mobile = (String) request.getSession().getAttribute("mobile");
			UserInfo user = userInfoService.getUserByMobile(mobile);
			String originalFileName = multipart.getOriginalFilename();
			if (multipart != null && originalFileName != null && originalFileName.length() > 0) {
				//File convFile = new File(uploadFilePath+multipart.getOriginalFilename());
				//multipart.transferTo(convFile);
				String newFileName = DateUtil.getFormatDateStr(DATE_PATTERN) + originalFileName.substring(originalFileName.lastIndexOf("."));
				File newFile = new File(uploadFilePath + newFileName);
				//Thumbnails.of(convFile).size(800, 1000).toFile(newFile);
				multipart.transferTo(newFile);
				detail = billOrderDetectorService.detetctBillOrderDetail(newFile, activityType, user);
				viewName="weui-confirm";
			}
		} catch (Exception se) {
			se.printStackTrace();
			viewName="weui-500";
		}

		ModelAndView mView = new ModelAndView(viewName);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(detail);
		mView.addObject("billDetail",json);
		return mView;
	}

	@RequestMapping(value = "/tess4j/merchant")
	@ResponseBody
	List<Merchant> getMerchantList(){
		Map<String, Merchant>  map = merchantService.getMerchantMap();
		List<Merchant> merchants = new ArrayList<>();
		for (Map.Entry<String, Merchant> entry : map.entrySet()) {
			merchants.add(entry.getValue());
		}
		return merchants;
	}
	
	@RequestMapping(value = "/tess4j/saveBillOrder", method = RequestMethod.POST)
	@ResponseBody
	ResponseResult saveForm(BillOrderDetail billOrderDetail, HttpServletRequest request)  {
	    String mobile = (String) request.getSession().getAttribute("mobile");
	    billOrderDetail.setMobile(mobile);
	    boolean flag = billOrderDetectorService.saveOrderDetail(billOrderDetail);
	    ResponseResult responseResult  = new ResponseResult();
	    responseResult.setStatus(flag);
	    responseResult.setView("/weiuiMsg");
	    return responseResult;
	    
	}
	
	
    @RequestMapping(value = "/tess4j/searchBillOrder", method = RequestMethod.POST)
    @ResponseBody
    ModelAndView search(@RequestParam("mobile") String mobile, HttpServletRequest request) throws JsonProcessingException {
        List<BillOrderDetail> billOrderDetails = billOrderDetectorService.getBillOrderDetailList(mobile);
        ModelAndView mView = new ModelAndView("weui-search-bar");
        mView.addObject("data", billOrderDetails);
        if(billOrderDetails.size()==0){
            mView.addObject("searchFlag", true);
        }
        mView.addObject("mobileNo", mobile);
        return mView;

    }
    

}