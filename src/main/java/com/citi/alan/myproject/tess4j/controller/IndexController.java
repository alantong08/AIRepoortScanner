package com.citi.alan.myproject.tess4j.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.citi.alan.myproject.tess4j.config.WebSecurityConfig;

@Controller
public class IndexController {
    
    @RequestMapping(value = { "", "/myproject", "/index" })
    public String index(HttpServletRequest request) {
        return "/tabbar";
    }

    @RequestMapping(value = { "/adminLogin" })
    public String adminLogin() {
        return "/admin-login";
    }

    @RequestMapping(value = { "/admin/home" })
    public String admin() {
        return "/admin/home";
    }

    @RequestMapping(value = { "/tabbar" })
    public String tabbar(HttpServletRequest request) {
        return "/tabbar";
    }

    @RequestMapping(value = { "/weuiLogin" })
    public String weuiLogin() {
        return "/weui-login";
    }

    @RequestMapping(value = { "/weuiRegister" })
    public String weuiRegister() {
        return "/weui-register";
    }

    @RequestMapping(value = { "/weuiUploadChart" })
    public String uploadChart() {
        return "/weui-upload-chart";
    }

    @RequestMapping(value = { "/weuiConfirm" })
    public String weuiConfirm() {
        return "/weui-confirm";
    }

    @RequestMapping(value = { "/weuiMsg" })
    public String weuiMsg() {
        return "/weui-msg";
    }

    @RequestMapping(value = { "/weiuiSearchBar" })
    public ModelAndView weiuiSearchBar(HttpServletRequest request) {
        String mobile = (String) request.getSession().getAttribute(WebSecurityConfig.SESSION_KEY);
        ModelAndView mView = new ModelAndView("weui-search-bar");
        mView.addObject("mobileNo", mobile);
        return mView;
    }

    @RequestMapping(value = { "/weuiRule" })
    public String weuiRule(HttpServletRequest request) {
        return "/weui-rule";
    }

    @RequestMapping(value = { "/weuiLogout" })
    public String weuiLogout(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/weui-login";
    }
    
    
    
    
//  
//    @RequestMapping(value={"register"})
//    public String register(){
//        return "register";
//    }
//    
//    
//    @RequestMapping(value={"upload"})
//    public String uplaod(){
//        return "/user/uploadOrderChart";
//    }
//
//    
//    @RequestMapping(value={"registerSuccess"})
//    public String registerSuccess(){
//        return "/user/registerSuccess";
//    }
//    
//    @RequestMapping(value={"/user/uploadOrderChart"})
//    public String uploadOrderChart(){
//        return "/user/uploadOrderChart";
//    }
//    
//    @RequestMapping(value={"/user/reportResult"})
//    public String reportResult(){ 
//        return "/user/reportResult";
//    }
//    
//    @RequestMapping(value={"/user/confirm"})
//    public String confirm(){ 
//        return "/user/confirm";
//    }

}
