package com.citi.alan.myproject.tess4j.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.UserInfo;

@Repository
public interface UserInfoDao extends CrudRepository<UserInfo, Long>{
    
    public UserInfo findByMobileAndPassword(String mobile, String password);
    
    public UserInfo findByMobile(String mobile);
    
    public UserInfo findByUserName(String userName);
    
    
   

}
