package com.citi.alan.myproject.tess4j.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.citi.alan.myproject.tess4j.config.WebSecurityConfig;
import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.model.UserLoginDetail;
import com.citi.alan.myproject.tess4j.service.api.UserInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RequestMapping("/loginRegister")
@RestController
public class UserLoginController {
	
	private static Logger logger = Logger.getLogger(UserLoginController.class);
	
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginDetail login(@RequestParam("mobile") String mobile,@RequestParam("password") String password, HttpSession session) {
        logger.info("mobile No. is :"+mobile);
        String viewName = "";
        UserLoginDetail userLoginDetail = null;  
        try {
            userLoginDetail = userInfoService.login(mobile, password);
            if (userLoginDetail.getMobile() != null) {       
                session.setAttribute(WebSecurityConfig.SESSION_KEY, mobile);
                viewName = "tabbar";
            }else{
            	userLoginDetail.setMessage("failed");
                viewName = "weuiLogin";
            }
           
            userLoginDetail.setView(viewName);
        } catch (Exception e) {
            logger.error(e);
        }
        return userLoginDetail;
    }

    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserLoginDetail registerPage(@RequestParam Map<String, String> data, HttpSession session) {
       
        String viweName = null;
        UserLoginDetail userLoginDetail = null;
        try {
            UserInfo userInfo = new UserInfo();
            BeanUtils.populate(userInfo, data);
            userLoginDetail = userInfoService.register(userInfo);
            if (userLoginDetail.isRegistered()) {
                viweName = "weuiRegister"; 
            }else{
                session.setAttribute(WebSecurityConfig.SESSION_KEY, userInfo.getMobile());
                viweName = "tabbar"; 
            }
            userLoginDetail.setView(viweName);

        } catch (Exception e) {
            logger.error(e);
        }
        return userLoginDetail;
    }
    
    @RequestMapping(value = "/weuiMyself")
	public ModelAndView loadUserInfo(HttpServletRequest request) throws JsonProcessingException {
		ModelAndView mView = new ModelAndView("");
		String viweName = null;
		UserLoginDetail userLoginDetail = new UserLoginDetail();
		try {
			String mobile = (String) request.getSession().getAttribute(WebSecurityConfig.SESSION_KEY);
			UserInfo userInfo = userInfoService.getUserByMobile(mobile);
			if (userInfo != null) {
				BeanUtils.copyProperties(userLoginDetail, userInfo);
				viweName = "weui-myself";
			} else {
				viweName = "weui-login";
			}
			mView.setViewName(viweName);

		} catch (Exception e) {
			logger.error(e);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(userLoginDetail);
		mView.addObject("userInfo", json);
		return mView;
	}
    
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(@RequestParam Map<String, String> data) {      
        String result = "";
        try {
            UserInfo userInfo = new UserInfo();
            BeanUtils.populate(userInfo, data);
            result = userInfoService.updateUserInfo(userInfo);
        } catch (Exception e) {
        		logger.error(e);
        }
        return result;
    }

}
