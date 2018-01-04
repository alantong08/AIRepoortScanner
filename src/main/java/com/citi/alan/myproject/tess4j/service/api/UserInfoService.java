package com.citi.alan.myproject.tess4j.service.api;

import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.model.UserLoginDetail;

public interface UserInfoService {

 
    public UserLoginDetail login(String mobile, String password);
    
    public UserLoginDetail register(UserInfo userInfo);
    
    public UserInfo getUserByMobile(String mobile);
    
    public String updateUserInfo(UserInfo userInfo);
    
    
}
