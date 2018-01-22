package com.citi.alan.myproject.tess4j.service.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.model.BillOrderDetail;

public interface BillOrderDetectorService {

    public BillOrderDetail detetctBillOrderDetail(File file, String rate, UserInfo user);
    
    public boolean saveOrderDetail(BillOrderDetail billOrderDetail);
    
    public List<BillOrderDetail> getBillOrderDetailList(String mobile);
    
    public Map<String, Object> getBillOrderDetailList(String id, String userName, String reportDateFrom, String reportDateTo, Pageable pagRequest);
    
    public List<BillOrderDetail> getExportingBillOrderDetailList(String reportDateFrom, String reportDateTo);

    public boolean updateOrderDetail(BillOrderDetail billOrderDetail) ;
    
    
   
}
