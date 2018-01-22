package com.citi.alan.myproject.tess4j.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.alan.myproject.tess4j.entity.OrderDetail;
import com.citi.alan.myproject.tess4j.entity.UserInfo;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer>{
    
    public List<OrderDetail> findByUserInfoOrderByCreatedDateDesc(UserInfo userInfo);
    
    public List<OrderDetail> findByUserInfoMobile(String mobile, Pageable pagRequest);
    
    public Page<OrderDetail> findAll(Specification<OrderDetail> s, Pageable pagRequest);
    
    @Query("from OrderDetail detail where detail.createdDate>=?1 and detail.createdDate <=?2 order by detail.createdDate desc")
    public List<OrderDetail> getReportByFilter(String reportDatFrom, String reportDatTo);
    
}
