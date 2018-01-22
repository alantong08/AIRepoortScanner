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
import org.springframework.data.domain.Sort.Direction;
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


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginDetail login(@RequestParam("mobile") String mobile,@RequestParam("password") String password, HttpSession session) {
        String viewName = "";
        UserLoginDetail userLoginDetail = new UserLoginDetail();  
        try {
            if ("18918297673".equals(mobile) && "admin".equals(password)) {     
                userLoginDetail.setMessage("success");
                viewName = "admin/home";
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
        String scanDateFrom=request.getParameter("scanDateFrom");
        String scanDateTo=request.getParameter("scanDateTo");
        String name=request.getParameter("name");
        String id=request.getParameter("id");
        logger.info("id:"+id+"\t from Date:"+scanDateFrom+" \t to Date:"+scanDateTo+"\t name:"+name);
        
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
            Pageable pagRequest = new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createdDate"));
            map = billOrderDetectorService.getBillOrderDetailList(id, name, scanDateFrom, scanDateTo,pagRequest);

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
