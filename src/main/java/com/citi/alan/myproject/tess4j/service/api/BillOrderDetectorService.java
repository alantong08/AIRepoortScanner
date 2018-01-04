package com.citi.alan.myproject.tess4j.service.api;

import java.io.File;
import java.util.List;

import com.citi.alan.myproject.tess4j.entity.UserInfo;
import com.citi.alan.myproject.tess4j.model.BillOrderDetail;

public interface BillOrderDetectorService {

    public BillOrderDetail detetctBillOrderDetail(File file, String rate, UserInfo user);
    
    public boolean saveOrderDetail(BillOrderDetail billOrderDetail);
    
    public List<BillOrderDetail> getBillOrderDetailList(String mobile);
    
    public List<BillOrderDetail> getBillOrderDetailList(String userName, String scanDate);
    
    public boolean updateOrderDetail(BillOrderDetail billOrderDetail) ;
    
   
}
