package com.citi.alan.myproject.tess4j.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

public class BillOrderDetail implements Serializable{

    private static final long serialVersionUID = 1L;
    @Excel(name= "扫描日期", width=20, isImportField="trus_st")
    private String scanDate;
    @Excel(name= "姓名", width=20, isImportField="trus_st")
    private String userName;
    @Excel(name= "群昵称", width=20, isImportField="trus_st")
    private String nickName;
    @Excel(name= "订单号", width=40, isImportField="trus_st")
    private String orderNum;
    @Excel(name= "商户名", width=20, isImportField="trus_st")
    private String merchantName;
    @Excel(name= "月底助攻", width=30, replace={"月底助攻_1", "月底不助攻_2" }, isImportField="trus_st")
    private String activityType;
    @Excel(name= "费率", width=20, isImportField="trus_st")
    private String rate; 
    @Excel(name= "扫码额度", width=20, isImportField="trus_st")
    private String actualPrice;
    private String discountedPrice;
    private String totalPrice;
    @Excel(name= "月底助攻", width=30, replace={"阿联_1", "阿信_2" , "阿宝_3" }, isImportField="trus_st")
    private String transferType;
    @Excel(name= "备注", width=20, isImportField="trus_st")
    private String comment;
    @Excel(name= "支付宝", width=20, isImportField="trus_st")
    private String alipayAccount;
    private String mobile;
    private String createdDate;
    private Integer id;
    private String groupName;
    private String imageName;
    
    

    public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getScanDate() {
        return scanDate;
    }
    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}


    public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	
	public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
      
    }
}
