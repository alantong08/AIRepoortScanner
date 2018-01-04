package com.citi.alan.myproject.tess4j.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.citi.alan.myproject.tess4j.dao.RoleDao;
import com.citi.alan.myproject.tess4j.dao.UserInfoDao;
import com.citi.alan.myproject.tess4j.entity.Role;
import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.model.UserLoginDetail;
import com.citi.alan.myproject.tess4j.service.api.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RoleDao roleDao;

    
    @Override
   public UserInfo getUserByMobile(String mobile){
       return  userInfoDao.findByMobile(mobile);
   }

    @Override
    public UserLoginDetail login(String mobile, String password) {
        UserInfo userInfo = userInfoDao.findByMobileAndPassword(mobile, password);
        UserLoginDetail userLoginDetail = new UserLoginDetail();
        if(userInfo!=null)
            BeanUtils.copyProperties(userInfo, userLoginDetail);
        return userLoginDetail;
    }

    @Override
    public UserLoginDetail register(UserInfo data) {
        UserInfo userInfo = userInfoDao.findByMobile(data.getMobile());
        UserLoginDetail userLoginDetail = new UserLoginDetail();
        if(userInfo!=null){ 
            userLoginDetail.setRegistered(true);
        }else{
        		Role role = roleDao.findByRoleName("USER");
        		HashSet<Role> roleSet = new HashSet<Role>();
        		roleSet.add(role);
        		data.setRoles(roleSet);
            userInfoDao.save(data);
        }
        return userLoginDetail;
    }
    
    public String updateUserInfo(UserInfo userInfo) {
    		String result = "";
        UserInfo storedUserInfo = getUserByMobile(userInfo.getMobile());
        if (storedUserInfo!=null) {
        		storedUserInfo.setAlipayAccount(userInfo.getAlipayAccount());
        		storedUserInfo.setNickName(userInfo.getNickName());
        		storedUserInfo.setPassword(userInfo.getPassword());
        		storedUserInfo.setGroupName(userInfo.getGroupName());
        		userInfoDao.save(storedUserInfo);
        		result = "success";
        }else{
        		result = "failed";
        }
        return result;
    		
    }

}
