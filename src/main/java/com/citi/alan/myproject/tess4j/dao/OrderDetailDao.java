package com.citi.alan.myproject.tess4j.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.OrderDetail;
import com.citi.alan.myproject.tess4j.entity.UserInfo;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer>{
    
    public List<OrderDetail> findByUserInfoOrderByCreatedDateDesc(UserInfo userInfo);
    
    public List<OrderDetail> findByUserInfoMobile(String mobile, Pageable pagRequest);
    
    public List<OrderDetail> findByScanDateOrderByCreatedDateDesc(String scanDate);

    public List<OrderDetail> findAll();
    
    public Page<OrderDetail> findAll(Pageable pagRequest);
    
    public Page<OrderDetail> findByScanDateOrderByCreatedDateDesc(String scanDate, Pageable pagRequest);
    
    public Page<OrderDetail> findByScanDateAndUserInfoUserNameOrderByCreatedDateDesc(String scanDate, String userName, Pageable pagRequest);
    
    public Page<OrderDetail> findByUserInfoUserNameOrderByCreatedDateDesc(String userName, Pageable pagRequest);
    
}
