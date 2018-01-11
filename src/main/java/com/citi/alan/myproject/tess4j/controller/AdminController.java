package com.citi.alan.myproject.tess4j.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citi.alan.myproject.tess4j.config.WebSecurityConfig;
import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.model.UserLoginDetail;
import com.citi.alan.myproject.tess4j.service.api.BillOrderDetectorService;
import com.citi.alan.myproject.tess4j.service.api.UserInfoService;
@RequestMapping("/admin")
@RestController
public class AdminController {
	
	private static Logger logger = Logger.getLogger(AdminController.class);
	
    @Autowired
    private BillOrderDetectorService billOrderDetectorService;


    @Autowired
    private UserInfoService userInfoService;
    

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginDetail login(@RequestParam("mobile") String mobile,@RequestParam("password") String password, HttpSession session) {
        String viewName = "";
        UserLoginDetail userLoginDetail = null;  
        try { 
            userLoginDetail = userInfoService.login(mobile, password);
            if (userLoginDetail.getMobile() != null) {     
                userLoginDetail.setMessage("success");
                viewName = "/admin/home";
                session.setAttribute(WebSecurityConfig.SESSION_KEY, mobile);
            }else{
                userLoginDetail.setMessage("failed");               
            }
            userLoginDetail.setView(viewName);
        } catch (Exception e) {
            logger.error(e);
        }
        return userLoginDetail;
    }

    
    
    @RequestMapping(value = "/getOrderList")
    public Map<String, Object> getOrderList(HttpServletRequest request ) {
        String scanDate=request.getParameter("scanDate");
        String name=request.getParameter("name");
        logger.info("scanDate:"+scanDate+" name:"+name);
        
        int pageNo=1;
        if(request.getParameter("page")!=null){
            pageNo=Integer.valueOf(request.getParameter("page"));
        }
        
        int pageSize=3;
        if(request.getParameter("rows")!=null){
            pageSize=Integer.valueOf(request.getParameter("rows"));
        }
        
         Map<String, Object> map = new HashMap<>();
        try { 
            Pageable pagRequest = new PageRequest(pageNo-1, pageSize, new Sort(Sort.Direction.DESC,"createdDate"));
            map = billOrderDetectorService.getBillOrderDetailList(name, scanDate, pagRequest);

        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }
    
    @RequestMapping(value = "/saveOrder")
    public String saveOrderList(BillOrderDetail billOrderDetail) { 
        try { 
            Boolean flag = billOrderDetectorService.updateOrderDetail(billOrderDetail);
            logger.info(flag);

        } catch (Exception e) {
            logger.error(e);
        }
        return "success";
    }
    


}
