package com.citi.alan.myproject.tess4j.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BillOrderDetail implements Serializable{

    private static final long serialVersionUID = 1L;
    private String scanDate;
    private String userName;
    private String nickName;
    private String orderNum;
    private String merchantName;
    private String activityType;
    private String rate; 
    private String actualPrice;
    private String discountedPrice;
    private String totalPrice;
    private String transferType;
    private String comments;
    private String alipayAccount;
    private String mobile;
    private String createdDate;
    private Integer id;
    private String groupName;
    
    

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
	public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
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
