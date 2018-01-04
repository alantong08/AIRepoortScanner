package com.citi.alan.myproject.tess4j.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citi.alan.myproject.tess4j.model.BillOrderDetail;
import com.citi.alan.myproject.tess4j.model.UserLoginDetail;
import com.citi.alan.myproject.tess4j.service.api.BillOrderDetectorService;
import com.citi.alan.myproject.tess4j.service.api.UserInfoService;
@RequestMapping("/admin")
@RestController
public class AdminController {
    
    @Autowired
    private BillOrderDetectorService billOrderDetectorService;


    @Autowired
    private UserInfoService userInfoService;
    

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginDetail login(@RequestParam("mobile") String mobile,@RequestParam("password") String password, HttpServletRequest request ) {
        
        request.getSession().setAttribute("mobile", mobile);
        String viewName = "";
        UserLoginDetail userLoginDetail = null;  
        try { 
            userLoginDetail = userInfoService.login(mobile, password);
            if (userLoginDetail.getMobile() != null) {     
                userLoginDetail.setMessage("success");
                viewName = "/admin/home";
            }else{
                userLoginDetail.setMessage("failed");
               
            }
            userLoginDetail.setView(viewName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLoginDetail;
    }

    
    
    @RequestMapping(value = "/getOrderList")
    public List<BillOrderDetail> getOrderList(HttpServletRequest request ) {
        String scanDate=request.getParameter("scanDate");
        String name=request.getParameter("name");
        System.out.println("scanDate:"+scanDate+" name:"+name);
         List<BillOrderDetail> billOrderDetails = new ArrayList<>();
        try { 
            billOrderDetails = billOrderDetectorService.getBillOrderDetailList(name, scanDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return billOrderDetails;
    }
    
    @RequestMapping(value = "/saveOrder")
    public String saveOrderList(BillOrderDetail billOrderDetail, HttpServletRequest request ) { 
        try { 
            Boolean flag = billOrderDetectorService.updateOrderDetail(billOrderDetail);
            System.out.println(flag);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
