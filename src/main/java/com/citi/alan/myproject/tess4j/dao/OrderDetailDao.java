package com.citi.alan.myproject.tess4j.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.OrderDetail;
import com.citi.alan.myproject.tess4j.entity.UserInfo;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer>{
    
    public List<OrderDetail> findByUserInfoOrderByCreatedDateDesc(UserInfo userInfo);
    
    public List<OrderDetail> findByUserInfoMobileOrderByCreatedDateDesc(String mobile);
    
    public List<OrderDetail> findByUserInfoUserNameOrderByCreatedDateDesc(String userName);
    
    public List<OrderDetail> findAll();
    
    public List<OrderDetail> findAll(Pageable pageable);
    
    public List<OrderDetail> findByScanDateOrderByCreatedDateDesc(String scanDate);
    
    public List<OrderDetail> findByScanDateAndUserInfoUserNameOrderByCreatedDateDesc(String scanDate, String userName);
    

}
