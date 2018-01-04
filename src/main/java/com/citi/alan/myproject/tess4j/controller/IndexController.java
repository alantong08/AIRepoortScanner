package com.citi.alan.myproject.tess4j.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    
    @RequestMapping(value={"","/","index"})
    public String index(HttpServletRequest request){
    	Object mobile = request.getSession().getAttribute("mobile");
    	if(mobile==null)
        return "weui-login";
    	else
    		return "tabbar";
    }
    
    
    
    @RequestMapping(value={"adminLogin"})
    public String adminLogin(){
        return "admin-login";
    }
    
    
    @RequestMapping(value={"/admin/home"})
    public String admin(){
        return "/admin/home";
    }
    
    
    
    @RequestMapping(value={"register"})
    public String register(){
        return "register";
    }
    
    
    @RequestMapping(value={"upload"})
    public String uplaod(){
        return "/user/uploadOrderChart";
    }

    
    @RequestMapping(value={"registerSuccess"})
    public String registerSuccess(){
        return "/user/registerSuccess";
    }
    
    @RequestMapping(value={"/user/uploadOrderChart"})
    public String uploadOrderChart(){
        return "/user/uploadOrderChart";
    }
    
    @RequestMapping(value={"/user/reportResult"})
    public String reportResult(){ 
        return "/user/reportResult";
    }
    
    @RequestMapping(value={"/user/confirm"})
    public String confirm(){ 
        return "/user/confirm";
    }
    
    @RequestMapping(value={"tabbar"})
    public String tabbar(HttpServletRequest request){ 
    	Object mobile = request.getSession().getAttribute("mobile");
    	if(mobile==null)
        return "weui-login";
    	else
    		return "tabbar";
    

    }
    
    @RequestMapping(value={"weuiLogin"})
    public String weuiLogin(){ 
        return "/weui-login";
    }
    
    @RequestMapping(value={"weuiRegister"})
    public String weuiRegister(){
        return "/weui-register";
    }
    
    @RequestMapping(value={"weuiUploadChart"})
    public String uploadChart(){
        return "/weui-upload-chart";
    }
    
    @RequestMapping(value={"weuiConfirm"})
    public String weuiConfirm(){
        return "/weui-confirm";
    }
    
    @RequestMapping(value={"weiuiMsg"})
    public String weiuiMsg(){
        return "/weui-msg";
    }
    
    @RequestMapping(value={"/weiuiSearchBar"})
    public ModelAndView weiuiSearchBar(HttpServletRequest request){
    		String mobile = (String) request.getSession().getAttribute("mobile");
    		ModelAndView mView = new ModelAndView("weui-search-bar");
    		mView.addObject("mobileNo",mobile);
    		return mView;
    }

    @RequestMapping(value={"/weuiRule"})
    public String weiuiRule(HttpServletRequest request){
        return "/weui-rule";
    }

}
